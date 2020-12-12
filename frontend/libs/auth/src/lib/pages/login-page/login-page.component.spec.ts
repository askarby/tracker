import { Spectator, createComponentFactory, mockProvider } from '@ngneat/spectator/jest';

import { LoginPageComponent } from './login-page.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginFormComponent } from '../../components/login-form/login-form.component';
import { MockComponents } from 'ng-mocks';
import { provideMockStore } from '@ngrx/store/testing';
import { HttpClient } from '@angular/common/http';
import { EMPTY } from 'rxjs';
import { SocialLoginPanelComponent } from '../../components/social-login-panel/social-login-panel.component';

describe('LoginPageComponent', () => {
  const createComponent = createComponentFactory({
    component: LoginPageComponent,
    declarations: [
      MockComponents(LoginFormComponent, SocialLoginPanelComponent)
    ],
    providers: [
      provideMockStore(),
      mockProvider(HttpClient, {
        get: EMPTY
      })
    ],
    imports: [
      ReactiveFormsModule
    ]
  });

  let spectator: Spectator<LoginPageComponent>;
  let component: LoginPageComponent;

  beforeEach(() => {
    spectator = createComponent();
    component = spectator.component;
  })

  it('should be create', () => {
    expect(spectator.component).toBeTruthy();
  });
});
