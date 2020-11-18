import { Actions, createEffect, ofType, OnInitEffects } from '@ngrx/effects';
import { TokenActions } from './token.actions';
import { catchError, map, switchMap, withLatestFrom } from 'rxjs/operators';
import { AuthService } from '../../services/auth.service';
import { Injectable } from '@angular/core';
import { of, throwError } from 'rxjs';
import { LocalStorageService } from '@tracker/shared-utils';
import { AuthSuccess } from '../../model/auth-success.model';
import { Action, Store } from '@ngrx/store';
import { TokenSelectors } from './token.selectors';
import { AuthActions } from '../auth.actions';

@Injectable({ providedIn: 'root' })
export class TokenEffects {
  static readonly LocaleStorageKey = 'token';

  initialize$ = createEffect(() => this.actions$.pipe(
    ofType(AuthActions.initialize),
    switchMap(() =>
      this.localStorageService.get<AuthSuccess>(TokenEffects.LocaleStorageKey).pipe(
        map((data) => {
          if (data && !this.hasExpired(data)) {
            return TokenActions.loginSuccess({ data });
          } else {
            return AuthActions.logout();
          }
        })
      )
    )
  ));

  login$ = createEffect(() => this.actions$.pipe(
    ofType(AuthActions.login),
    switchMap((action) =>
      this.authService.login(action.username, action.password).pipe(
        map((data) => TokenActions.loginSuccess({ data })),
        catchError((error) => of(TokenActions.loginFailed({ error })))
      )
    )
  ));

  refresh$ = createEffect(() => this.actions$.pipe(
    ofType(TokenActions.refresh),
    withLatestFrom(this.store.select(TokenSelectors.selectRefreshToken)),
    switchMap(([action, token]) => {
      if (token) {
        return this.authService.refreshToken(token).pipe(
          map((data) => TokenActions.refreshSuccess({ data })),
          catchError((error) => of(TokenActions.refreshFailed({ error })))
        );
      } else {
        return throwError('Unable to renew access token, when having no refresh token');
      }
    })
  ));

  logout$ = createEffect(() => this.actions$.pipe(
    ofType(AuthActions.logout, TokenActions.refreshFailed),
    switchMap(() => this.localStorageService.remove(TokenEffects.LocaleStorageKey).pipe(
      map(() => TokenActions.logoutSuccess()),
      catchError((error) => of(TokenActions.logoutFailed({ error })))
    ))
  ));

  saveToken$ = createEffect(() => this.actions$.pipe(
    ofType(TokenActions.loginSuccess, TokenActions.refreshSuccess),
    switchMap((action) => {
      return this.localStorageService.set(TokenEffects.LocaleStorageKey, action.data);
    })
  ), { dispatch: false });

  constructor(private actions$: Actions,
              private store: Store<any>,
              private authService: AuthService,
              private localStorageService: LocalStorageService) {
  }

  private hasExpired(data: AuthSuccess) {
    // TODO: Make sure to compensate for countdown delay (don't start of by logging in if countdown is less than 1 minute away)
    if (data && data.parsedAccessToken.exp * 1000 < Date.now()) {
      return true;
    } else {
      return false;
    }
  }
}
