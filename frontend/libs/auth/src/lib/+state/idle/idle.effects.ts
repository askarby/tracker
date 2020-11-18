import { Inject, Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Action, Store } from '@ngrx/store';
import { exhaustMap, filter, first, map, startWith, switchMap, takeUntil, withLatestFrom } from 'rxjs/operators';
import { fromEvent, interval, merge, Observable, of, timer } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IdleSelectors } from './idle.selectors';
import { Milliseconds, Seconds } from '@tracker/shared-utils';
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
    withLatestFrom(this.store.select(IdleSelectors.selectTimeout)),
    exhaustMap(([action, timeoutMilliseconds]) => {
      return interval(Milliseconds.fromSeconds(1)).pipe(
        withLatestFrom(this.browserActivityTimestamp()),
        map(([_, lastEvent]) => Date.now() - lastEvent),
        filter((elapsed) => {
          // We'll round this, since timeout lacks a bit of precision,
          // a timeout of 1000ms may take 1002ms or more.
          const elapsedSeconds = Math.round(elapsed / 1000);
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
              @Inject(AUTH_CONFIG_TOKEN) private config: AuthModuleConfig) {
  }

  private browserActivityTimestamp(): Observable<number> {
    return merge(
      fromEvent(document, 'click'),
      fromEvent(document, 'wheel'),
      fromEvent(document, 'scroll'),
      fromEvent(document, 'mousemove'),
      fromEvent(document, 'keyup'),
      fromEvent(window, 'resize'),
      fromEvent(window, 'scroll'),
      fromEvent(window, 'mousemove')
    ).pipe(
      startWith(undefined),
      map(() => Date.now())
    );
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
