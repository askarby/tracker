import { createSelector } from '@ngrx/store';
import { TokenStatus } from './token.state';
import { AuthSelectors } from '../auth.selectors';

const selectTokenState = createSelector(
  AuthSelectors.selectAuthState,
  (auth) => auth.token,
);

export const TokenSelectors = {
  /**
   * Retrieves the JWT access token, or null if none is present
   */
  selectAccessToken: createSelector(
    selectTokenState,
    (state) => state.accessToken?.raw || null
  ),

  /**
   * Retrieves the expiration of the JWT access token (as milliseconds since unix epoch),
   * that is, it's comparable to `Date.now()`.
   */
  selectAccessTokenExpiration: createSelector(
    selectTokenState,
    (state) => {
      const expiration = state.accessToken?.expiresAt;
      return expiration ? expiration * 1000 : null;
    }
  ),

  selectRefreshToken: createSelector(
    selectTokenState,
    (state) => state.refreshToken
  ),

  isInitializing: createSelector(
    selectTokenState,
    (state) => state.status === TokenStatus.INITIALIZING,
  ),

  isLoggedIn: createSelector(
    selectTokenState,
    (state) => state.status === TokenStatus.LOGGED_IN,
  ),

  isLoggedOut: createSelector(
    selectTokenState,
    (state) => state.status === TokenStatus.LOGGED_OUT,
  )
}
