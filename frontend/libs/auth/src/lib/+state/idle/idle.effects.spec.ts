import { createServiceFactory, SpectatorService, SpyObject } from '@ngneat/spectator/jest';
import { IdleEffects } from './idle.effects';
import { provideMockActions } from '@ngrx/effects/testing';
import { EMPTY, Observable, Subject } from 'rxjs';
import { Action } from '@ngrx/store';
import { provideMockStore } from '@ngrx/store/testing';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BrowserActivityService, Milliseconds } from '@tracker/shared-utils';
import { IdleSelectors } from './idle.selectors';
import { CountdownModalResponse } from '../../modals/countdown-modal/countdown-modal.component';
import { provideAuthDefaultConfig } from '../../testing/auth-config.test-data';

describe('IdleEffects', () => {
  const createEffects = createServiceFactory({
    service: IdleEffects,
    mocks: [
      NgbModal,
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

  let closed: Subject<CountdownModalResponse>;
  let dismissed: Subject<CountdownModalResponse>;
  let modalService: SpyObject<NgbModal>;

  let activityService: SpyObject<BrowserActivityService>;

  beforeEach(() => {
    spectator = createEffects();
    effects = spectator.service;
    actions$ = EMPTY;

    closed = new Subject<CountdownModalResponse>();
    dismissed = new Subject<CountdownModalResponse>();

    modalService = spectator.inject(NgbModal);
    modalService.open.andReturn({
      closed: closed.asObservable(),
      dismissed: dismissed.asObservable()
    });

    activityService = spectator.inject(BrowserActivityService);
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
});
