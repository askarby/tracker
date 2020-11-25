import { createServiceFactory, SpectatorService } from '@ngneat/spectator';
import { BrowserActivityService } from './browser-activity.service';

describe('BrowserActivityService', () => {
  let spectator: SpectatorService<BrowserActivityService>;
  const createService = createServiceFactory(BrowserActivityService);

  beforeEach(() => spectator = createService());

  it('should...', () => {
    expect(spectator.service).toBeTruthy();
  });
});