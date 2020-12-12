import { JwtToken } from './jwt-token.model';

export interface AuthSuccess {
  tokenType: 'Bearer';

  accessToken: string;
  refreshToken: string;
  parsedAccessToken: JwtToken;

  expiresIn: number;
  username: string;
}
