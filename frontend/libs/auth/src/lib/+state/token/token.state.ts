export interface AccessToken {
  raw: string;
  username: string;
  roles: string[];
  expiresAt: number;
}

export enum TokenStatus {
  INITIALIZING = 'INITIALIZING',
  LOGGED_IN = 'LOGGED_IN',
  LOGGED_OUT = 'LOGGED_OUT'
}

export interface TokenState {
  accessToken: AccessToken | null;
  refreshToken: string | null;

  status: TokenStatus,
  error: any;
}

export function createInitialTokenState(): TokenState {
  return {
    accessToken: null,
    refreshToken: null,

    status: TokenStatus.INITIALIZING,
    error: null
  };
}
