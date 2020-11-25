import { Inject, Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Action, Store } from '@ngrx/store';
import { exhaustMap, filter, first, map, switchMap, takeUntil, withLatestFrom } from 'rxjs/operators';
import { interval, merge, Observable, of } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IdleSelectors } from './idle.selectors';
import { BrowserActivityService, Milliseconds } from '@tracker/shared-utils';
import { TokenActions } from '../token/token.actions';
import {
  CountdownModalComponent,
  CountdownModalResponse
} from '../../modals/countdown-modal/countdown-modal.component';
import { AuthActions } from '../auth.actions';
import { AUTH_CONFIG_TOKEN, AuthModuleConfig } from '../../auth.config';

@Injectable({ providedIn: 'root' })
export class IdleEffects {

  idle$ = createEffect(() => this.actions$.pipe(
    ofType(TokenActions.loginSuccess),
    withLatestFrom(this.store.select(IdleSelectors.selectCustomTimeoutMilliseconds)),
    exhaustMap(([action, customTimeoutMilliseconds]) => {
      return interval(Milliseconds.fromSeconds(1)).pipe(
        withLatestFrom(this.activityService.anyActivityTimestamp$()),
        map(([_, lastEvent]) => Date.now() - lastEvent),
        filter((elapsed) => {
          // We'll round this, since timeout lacks a bit of precision,
          // a timeout of 1000ms may take 1002ms or more.
          const elapsedSeconds = Math.round(elapsed / 1000);
          const timeoutMilliseconds = customTimeoutMilliseconds || this.config.idleTimeoutMilliseconds;
          const timeoutSeconds = Math.round((timeoutMilliseconds - this.config.idleCountdownMilliseconds) / 1000);
          return elapsedSeconds > 0 && elapsedSeconds % timeoutSeconds === 0;
        }),
        exhaustMap(() => this.openCountdownModal()),
        filter((response) => !!response),
        takeUntil(this.isLoggedOut())
      );
    })
  ));

  constructor(private actions$: Actions,
              private store: Store<any>,
              private modalService: NgbModal,
              private activityService: BrowserActivityService,
              @Inject(AUTH_CONFIG_TOKEN) private config: AuthModuleConfig) {
  }

  private isLoggedOut() {
    return this.actions$.pipe(ofType(AuthActions.logout));
  }

  private openCountdownModal(): Observable<Action> {
    const ref = this.modalService.open(CountdownModalComponent);
    return merge(ref.closed, ref.dismissed).pipe(
      first(),
      switchMap((response: CountdownModalResponse) => {
        if (response === CountdownModalResponse.Continue) {
          return [];
        } else {
          return of(AuthActions.logout());
        }
      })
    );
  }
}
