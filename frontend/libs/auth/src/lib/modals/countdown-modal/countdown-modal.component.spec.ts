import { Spectator, createComponentFactory } from '@ngneat/spectator';

import { CountdownModalComponent } from './countdown-modal.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { MockComponents } from 'ng-mocks';
import { CountdownPanelComponent } from '../../components/countdown-panel/countdown-panel.component';
import { AUTH_CONFIG_TOKEN, AuthModuleConfig } from '../../auth.config';

describe('CountdownModalComponent', () => {
  const createComponent = createComponentFactory({
    component: CountdownModalComponent,
    declarations: [
      MockComponents(CountdownPanelComponent),
    ],
    providers: [
      {
        provide: AUTH_CONFIG_TOKEN,
        useValue: {
          idleTimeoutMilliseconds: 5 * 60 * 1000,
          idleCountdownMilliseconds: 1 * 60 * 1000,
        }
      },
      {
        provide: NgbActiveModal,
        useValue: {

        }
      }
    ]
  });
  let spectator: Spectator<CountdownModalComponent>;

  it('should create', () => {
    spectator = createComponent();

    expect(spectator.component).toBeTruthy();
  });
});
