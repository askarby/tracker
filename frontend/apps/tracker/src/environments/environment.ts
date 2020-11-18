import { Environment } from './environment.model';

export const environment: Environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080',

  security: {
    idleTimeoutMilliseconds: 60 * 1000 * 5,
    idleCountdownMilliseconds: 60 * 1000 * 1,
  }
};
