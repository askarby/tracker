import { JwtToken } from '../../lib/models/jwt-token.model';
import merge from 'ts-deepmerge';

export function createJwtToken(override: Partial<JwtToken> = {}): JwtToken {
  const issuedAt = Date.now();
  const defaultJwtToken: JwtToken = {
    exp: issuedAt + (5 * 60 * 1000),
    iat: issuedAt,
    iss: 'tracker',
    nbf: issuedAt,
    roles: ['ROLE_USER'],
    sub: 'johndoe',
  };
  return merge(defaultJwtToken, override);
}
