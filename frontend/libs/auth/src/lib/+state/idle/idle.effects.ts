import { Inject, Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Action, Store } from '@ngrx/store';
import { exhaustMap, filter, first, map, switchMap, takeUntil, withLatestFrom } from 'rxjs/operators';
import { interval, Observable, of } from 'rxjs';
import { IdleSelectors } from './idle.selectors';
import { BrowserActivityService, Milliseconds } from '@tracker/shared-utils';
import { TokenActions } from '../token/token.actions';
import { CountdownModalComponent } from '../../modals/countdown-modal/countdown-modal.component';
import { AuthActions } from '../auth.actions';
import { AUTH_CONFIG_TOKEN, AuthModuleConfig } from '../../auth.config';
import { ModalService } from '@tracker/shell';

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
        takeUntil(this.isLoggedOut())
      );
    })
  ));

  constructor(private actions$: Actions,
              private store: Store<any>,
              private modalService: ModalService,
              private activityService: BrowserActivityService,
              @Inject(AUTH_CONFIG_TOKEN) private config: AuthModuleConfig) {
  }

  private isLoggedOut() {
    return this.actions$.pipe(ofType(AuthActions.logout));
  }

  private openCountdownModal(): Observable<Action> {
    return this.modalService.displayModal<Action | undefined>(CountdownModalComponent).pipe(
      first(),
      switchMap((response) => {
        return !!response ? of(response) : [];
      })
    );
  }
}
