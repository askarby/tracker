import { createServiceFactory, SpectatorService } from '@ngneat/spectator/jest';
import { LocalStorageService } from './local-storage.service';
import { WINDOW_TOKEN } from '../tokens/window.token';

describe('LocalStorageService', () => {
  const createService = createServiceFactory({
    service: LocalStorageService,
    providers: [
      {
        provide: WINDOW_TOKEN,
        useValue: {
          localStorage: {
            getItem: jest.fn(),
            setItem: jest.fn(),
            removeItem: jest.fn()
          }
        }
      }
    ]
  });
  let spectator: SpectatorService<LocalStorageService>;

  beforeEach(() => spectator = createService());

  it('should be created', () => {
    expect(spectator.service).toBeTruthy();
  });
});
