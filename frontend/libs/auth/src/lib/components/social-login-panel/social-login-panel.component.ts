import { ChangeDetectionStrategy, Component, Input } from '@angular/core';

@Component({
  selector: 'auth-social-login-panel',
  templateUrl: './social-login-panel.component.html',
  styleUrls: ['./social-login-panel.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SocialLoginPanelComponent {
  @Input()
  loginWithGithub = false;

  @Input()
  loginWithGoogle = false;

  get showSocialLogin() {
    return this.loginWithGithub || this.loginWithGoogle;
  }
}
