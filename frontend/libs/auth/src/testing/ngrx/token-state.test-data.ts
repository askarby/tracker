import merge from 'ts-deepmerge';
import { AccessToken, TokenState, TokenStatus } from '../../lib/+state/token/token.state';

export function createTokenState(override: Partial<TokenState> = {}): TokenState {
  const defaultState: TokenState = {
    accessToken: createAccessToken(),
    refreshToken: 'refresh-token',

    status: TokenStatus.LOGGED_IN,
    error: null,
  }
  return merge(defaultState, override);
}

export function createAccessToken(override: Partial<AccessToken> = {}): AccessToken {
  const defaultToken: AccessToken = {
    raw: 'raw',
    username: 'johndoe',
    roles: [ 'USER_ADMIN'],
    expiresAt: Date.now() + (60 * 60 * 1000),
  };
  return merge(defaultToken, override);
}
