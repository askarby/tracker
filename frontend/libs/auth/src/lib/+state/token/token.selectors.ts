import { createSelector } from '@ngrx/store';
import { TokenStatus } from './token.state';
import { AuthSelectors } from '../auth.selectors';

export namespace TokenSelectors {
  const selectTokenState = createSelector(
    AuthSelectors.selectAuthState,
    (auth) => auth.token,
  );

  /**
   * Retrieves the JWT access token, or null if none is present
   */
  export const selectAccessToken = createSelector(
    selectTokenState,
    (state) => state.accessToken?.raw || null
  );

  /**
   * Retrieves the expiration of the JWT access token (as milliseconds since unix epoch),
   * that is, it's comparable to `Date.now()`.
   */
  export const selectAccessTokenExpiration = createSelector(
    selectTokenState,
    (state) => {
      const expiration = state.accessToken?.expiresAt;
      return expiration ? expiration * 1000 : null;
    }
  );

  export const selectRefreshToken = createSelector(
    selectTokenState,
    (state) => state.refreshToken
  );

  export const isInitializing = createSelector(
    selectTokenState,
    (state) => state.status === TokenStatus.INITIALIZING,
  );

  export const isLoggedIn = createSelector(
    selectTokenState,
    (state) => state.status === TokenStatus.LOGGED_IN,
  );

  export const isLoggedOut = createSelector(
    selectTokenState,
    (state) => state.status === TokenStatus.LOGGED_OUT,
  );
}
