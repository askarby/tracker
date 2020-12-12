import { Spectator, createComponentFactory } from '@ngneat/spectator/jest';

import { SocialLoginPanelComponent } from './social-login-panel.component';

describe('SocialLoginPanelComponent', () => {
  let spectator: Spectator<SocialLoginPanelComponent>;
  const createComponent = createComponentFactory(SocialLoginPanelComponent);

  it('should create', () => {
    spectator = createComponent();

    expect(spectator.component).toBeTruthy();
  });
});
