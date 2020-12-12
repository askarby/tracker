import { createServiceFactory, SpectatorService, SpyObject } from '@ngneat/spectator/jest';
import { IdleEffects } from './idle.effects';
import { provideMockActions } from '@ngrx/effects/testing';
import { EMPTY, Observable, Subject } from 'rxjs';
import { Action } from '@ngrx/store';
import { provideMockStore } from '@ngrx/store/testing';
import { BrowserActivityService, Milliseconds } from '@tracker/shared-utils';
import { IdleSelectors } from './idle.selectors';
import { provideAuthDefaultConfig } from '../../../testing/auth-config.test-data';
import { ModalService } from '@tracker/shell';

describe('IdleEffects', () => {
  const createEffects = createServiceFactory({
    service: IdleEffects,
    mocks: [
      ModalService,
      BrowserActivityService
    ],
    providers: [
      provideMockActions(() => actions$),
      provideMockStore({
        selectors: [
          {
            selector: IdleSelectors.selectCustomTimeoutMilliseconds,
            value: Milliseconds.fromMinutes(10),
          }
        ]
      }),
      provideAuthDefaultConfig()
    ]
  });

  let spectator: SpectatorService<IdleEffects>;
  let effects: IdleEffects;
  let actions$: Observable<Action>;

  let closed: Subject<Action>;
  let modalService: SpyObject<ModalService>;

  let activityService: SpyObject<BrowserActivityService>;

  beforeEach(() => {
    spectator = createEffects();
    effects = spectator.service;
    actions$ = EMPTY;

    closed = new Subject<Action>();

    modalService = spectator.inject(ModalService);
    modalService.displayModal.andReturn(closed.asObservable());

    activityService = spectator.inject(BrowserActivityService);
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
});
