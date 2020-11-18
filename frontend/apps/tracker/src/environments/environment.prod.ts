import { Environment } from './environment.model';

export const environment: Environment = {
  production: true,
  apiBaseUrl: 'https://tracker-api.inno-tech.dk',

  security: {
    idleTimeoutMilliseconds: 60 * 1000 * 5,
    idleCountdownMilliseconds: 60 * 1000 * 1,
  }
};
