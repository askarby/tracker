import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { combineLatest, Observable, of } from 'rxjs';
import { TokenSelectors } from '../+state/token/token.selectors';
import { first, map, skipWhile, switchMap, tap } from 'rxjs/operators';
import { Inject, Injectable } from '@angular/core';
import { TokenActions } from '../+state/token/token.actions';
import { AUTH_CONFIG_TOKEN, AuthModuleConfig } from '../auth.config';

@Injectable()
export class BearerTokenInterceptor implements HttpInterceptor {
  private ignore: string[];

  constructor(@Inject(AUTH_CONFIG_TOKEN) private config: AuthModuleConfig,
              private store: Store<any>) {
    this.ignore = [
      this.config.urls.acquireToken,
      this.config.urls.refreshToken
    ]
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.ignore.includes(req.urlWithParams)) {
      return next.handle(req);
    } else {
      return combineLatest([
        this.store.select(TokenSelectors.selectAccessToken),
        this.store.select(TokenSelectors.selectAccessTokenExpiration)
      ]).pipe(
        first(),
        switchMap(([accessToken, expiresAt]) => {
          if (this.hasExpired(expiresAt)) {
            return this.refreshAccessToken(accessToken).pipe(
              tap((newToken) => console.log('refreshed and got new token:', newToken))
            );
          }
          return of(accessToken);
        }),
        map((accessToken) => {
          if (accessToken) {
            req = req.clone({
              headers: req.headers.set('Authorization', `Bearer ${accessToken}`)
            });
          }
          return req;
        }),
        switchMap((request) => {
          return next.handle(request);
        })
      );
    }
  }

  private hasExpired(expiresAt: number | null): boolean {
    const now = Date.now();
    const expired = expiresAt && expiresAt <= Date.now() || false;
    console.log(`hasExpired > expiresAt: ${expiresAt}, now: ${now} -> ${expired}`);
    return expired;
  }

  private refreshAccessToken(previousToken: string | null): Observable<string | null> {
    this.store.dispatch(TokenActions.refresh());
    return this.store.select(TokenSelectors.selectAccessToken).pipe(
      skipWhile((accessToken) => accessToken === previousToken),
      first(),
    );
  }
}
