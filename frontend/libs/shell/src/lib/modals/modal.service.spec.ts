import { createServiceFactory, SpectatorService } from '@ngneat/spectator/jest';
import { ModalService } from './modal.service';
import { BsModalService } from 'ngx-bootstrap/modal';

describe('ModalService', () => {
  const createService = createServiceFactory({
    service: ModalService,
    mocks: [BsModalService]
  });

  let spectator: SpectatorService<ModalService>;
  let service: ModalService;

  beforeEach(() => {
    spectator = createService();
    service = spectator.service;
  });

  it('should be created', () => {
    expect(spectator.service).toBeTruthy();
  });
});
