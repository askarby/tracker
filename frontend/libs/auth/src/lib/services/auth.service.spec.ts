import { AuthService } from './auth.service';
import { createHttpFactory, SpectatorHttp } from '@ngneat/spectator/jest';
import { AUTH_CONFIG_TOKEN } from '@tracker/auth';

describe('AuthService', () => {
  const createService = createHttpFactory({
    service: AuthService,
    providers: [
      {
        provide: AUTH_CONFIG_TOKEN,
        useValue: {
          urls: {
            acquireToken: 'http://127.0.0.1/acquire',
            refreshToken: 'http://127.0.0.1/refresh',
          }
        }
      }
    ]
  });

  let spectator: SpectatorHttp<AuthService>;
  let service: AuthService;

  beforeEach(() => {
    spectator = createService();
    service = spectator.service;
  });

  it('should be created', () => {
    expect(spectator.service).toBeTruthy();
  });
});
