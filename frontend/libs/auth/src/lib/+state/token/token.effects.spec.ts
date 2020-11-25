import { createServiceFactory, SpectatorService, SpyObject } from '@ngneat/spectator/jest';
import { provideMockActions } from '@ngrx/effects/testing';
import { Observable, of } from 'rxjs';
import { Action } from '@ngrx/store';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { LocalStorageService } from '@tracker/shared-utils';
import { provideAuthDefaultConfig } from '../../testing/auth-config.test-data';
import { TokenEffects } from './token.effects';
import { AuthService } from '../../services/auth.service';
import { TokenSelectors } from './token.selectors';
import { TokenActions } from './token.actions';
import { AuthActions } from '@tracker/auth';
import { cold, hot } from 'jasmine-marbles';
import { createAuthSuccess } from '../../testing/models/auth-success-model.test-data';
import { waitForAsync } from '@angular/core/testing';

describe('TokenEffects', () => {
  const createEffects = createServiceFactory({
    service: TokenEffects,
    mocks: [
      AuthService,
      LocalStorageService
    ],
    providers: [
      provideMockActions(() => actions$),
      provideMockStore({
        selectors: [
          {
            selector: TokenSelectors.selectRefreshToken,
            value: 'refresh-token',
          }
        ]
      }),
      provideAuthDefaultConfig()
    ]
  });

  let spectator: SpectatorService<TokenEffects>;
  let effects: TokenEffects;
  let actions$: Observable<Action>;

  let store: MockStore<any>;
  let authService: SpyObject<AuthService>;
  let localStorageService: SpyObject<LocalStorageService>;

  beforeEach(() => {
    spectator = createEffects();
    effects = spectator.service;

    store = spectator.inject(MockStore);
    authService = spectator.inject(AuthService);
    localStorageService = spectator.inject(LocalStorageService);
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });

  describe(`initialize$ (intercepting action with type "${AuthActions.initialize.type}")`, () => {
    it(`should dispatch action with type "${TokenActions.loginSuccess.type}" when valid token exists in local storage`, () => {
      actions$ = hot('a', {
        a: AuthActions.initialize()
      });

      const authSuccess = createAuthSuccess();
      localStorageService.get.mockReturnValue(
        cold('a|', { a: authSuccess })
      );

      const expected = hot('a', {
        a: TokenActions.loginSuccess({ data: authSuccess }),
      });

      expect(effects.initialize$).toBeObservable(expected);
    });

    it(`should dispatch action with type "${AuthActions.logout.type}" when no token is found in local storage`, () => {
      actions$ = hot('a', {
        a: AuthActions.initialize()
      });

      localStorageService.get.mockReturnValue(
        cold('a|', { a: null })
      );

      const expected = hot('a', {
        a: AuthActions.logout()
      });

      expect(effects.initialize$).toBeObservable(expected);
    });

    it(`should dispatch action with type "${AuthActions.logout.type}" when expired token exists in local storage`, () => {
      const authSuccess = createAuthSuccess();
      jest
        .useFakeTimers('modern')
        .setSystemTime((authSuccess.parsedAccessToken.exp + 1) * 1000 );

      actions$ = hot('a', {
        a: AuthActions.initialize()
      });

      localStorageService.get.mockReturnValue(
        cold('a|', { a: authSuccess })
      );

      const expected = hot('a', {
        a: AuthActions.logout()
      });

      expect(effects.initialize$).toBeObservable(expected);
    });
  });

  describe(`login$ (intercepting action with type "${AuthActions.login.type}")`, () => {
    it(`should dispatch action with type "${TokenActions.loginSuccess.type}" when able to login`, () => {
      actions$ = hot('a', {
        a: AuthActions.login({
          username: 'username',
          password: 'password',
        })
      });

      const authSuccess = createAuthSuccess();
      authService.login.mockReturnValue(
        cold('a|', { a: authSuccess })
      );

      const expected = hot('a', {
        a: TokenActions.loginSuccess({ data: authSuccess }),
      });

      expect(effects.login$).toBeObservable(expected);
    });

    it(`should dispatch action with type "${TokenActions.loginFailed.type}" when unable to login`, () => {
      actions$ = hot('a', {
        a: AuthActions.login({
          username: 'username',
          password: 'password',
        })
      });

      authService.login.mockReturnValue(
        cold('#|')
      );

      const expected = hot('a', {
        a: TokenActions.loginFailed({ error: 'error' }),
      });

      expect(effects.login$).toBeObservable(expected);
    });
  });

  describe(`refresh$ (intercepting action with type "${TokenActions.refresh.type}")`, () => {

    it(`should dispatch action with type "${TokenActions.refreshSuccess.type}" when able to refresh access token`, () => {
      actions$ = hot('a', {
        a: TokenActions.refresh()
      });

      const authSuccess = createAuthSuccess();
      authService.refreshToken.mockReturnValue(
        cold('a|', { a: authSuccess })
      );

      const expected = hot('a', {
        a: TokenActions.refreshSuccess({ data: authSuccess }),
      });

      expect(effects.refresh$).toBeObservable(expected);
      expect(authService.refreshToken).toHaveBeenCalledWith('refresh-token');
    });

    it(`should dispatch action with type "${TokenActions.refreshFailed.type}" when unable to refresh access token (remotely)`, () => {
      actions$ = hot('a', {
        a: TokenActions.refresh()
      });

      authService.refreshToken.mockReturnValue(
        cold('#|')
      );

      const expected = hot('a', {
        a: TokenActions.refreshFailed({ error: 'error' }),
      });

      expect(effects.refresh$).toBeObservable(expected);
    });

    it(`should produce error-Observable when unable to refresh access token (having no refresh token)`, () => {
      actions$ = hot('a', {
        a: TokenActions.refresh()
      });

      store.overrideSelector(TokenSelectors.selectRefreshToken, null);

      const expected = hot('#', null, 'Unable to renew access token, when having no refresh token');

      expect(effects.refresh$).toBeObservable(expected);
    });
  });

  describe(`logout$`, () => {
    [
      AuthActions.logout(),
      TokenActions.refreshFailed({ error: 'error' }),
    ].forEach((intercepted) => {
      describe(`(intercepting action with type "${intercepted.type}")`, () => {
        it(`should dispatch action with type "${TokenActions.logoutSuccess.type}" when able to logout`, () => {
          actions$ = hot('a', {
            a: intercepted
          });

          const fromStorage = createAuthSuccess();
          localStorageService.remove.mockReturnValue(
            cold('a|', { a: fromStorage })
          );

          const expected = hot('a', {
            a: TokenActions.logoutSuccess(),
          });

          expect(effects.logout$).toBeObservable(expected);
        });

        it(`should dispatch action with type "${TokenActions.logoutFailed.type}" when unable to logout`, () => {
          actions$ = hot('a', {
            a: intercepted
          });

          localStorageService.remove.mockReturnValue(
            cold('#|')
          );

          const expected = hot('a', {
            a: TokenActions.logoutFailed({ error: 'error' }),
          });

          expect(effects.logout$).toBeObservable(expected);
        });
      });
    });
  });

  describe(`saveToken$`, () => {
    [
      TokenActions.loginSuccess({ data: createAuthSuccess() }),
      TokenActions.refreshSuccess({ data: createAuthSuccess() }),
    ].forEach((intercepted) => {
      describe(`(intercepting action with type "${intercepted.type}")`, () => {
        it('should set token in local storage', () => {
          actions$ = of(intercepted);

          effects.saveToken$.subscribe();

          expect(localStorageService.set).toHaveBeenCalledWith(TokenEffects.LocaleStorageKey, intercepted.data);
        });
      });
    });
  });
});
