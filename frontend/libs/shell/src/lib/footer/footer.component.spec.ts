import { createRoutingFactory, Spectator } from '@ngneat/spectator/jest';

import { FooterComponent } from './footer.component';
import { SHELL_CONFIG_TOKEN } from '../shell.config';
import { createShellModuleConfig } from '../../testing/shell-config.test-data';

describe('FooterComponent', () => {
  const createComponent = createRoutingFactory({
    component: FooterComponent,
    providers: [
      {
        provide: SHELL_CONFIG_TOKEN,
        useValue: createShellModuleConfig(),
      }
    ]
  });

  let spectator: Spectator<FooterComponent>;
  let component: FooterComponent;

  beforeEach(() => {
    spectator = createComponent();
    component = spectator.component;
  });

  it('should be created', () => {
    expect(spectator.component).toBeTruthy();
  });
});
