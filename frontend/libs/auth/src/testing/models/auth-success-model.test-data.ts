import { AuthSuccess } from '../../lib/models/auth-success.model';
import merge from 'ts-deepmerge';
import { createJwtToken } from './jwt-token.test-data';
import { Milliseconds } from '@tracker/shared-utils';

export function createAuthSuccess(override: Partial<AuthSuccess> = {}): AuthSuccess {
  const accessToken = createJwtToken();
  const defaultAuthSuccess: AuthSuccess = {
    tokenType: 'Bearer',

    accessToken: 'accessToken',
    refreshToken: 'refreshToken',
    parsedAccessToken: accessToken,

    expiresIn: Milliseconds.fromMinutes(5),
    username: accessToken.sub,
  };
  return merge(defaultAuthSuccess, override);
}
