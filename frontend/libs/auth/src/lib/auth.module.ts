import { ModuleWithProviders, NgModule, InjectionToken } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';
import { tokenReducer } from './+state/token/token.reducer';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { SharedUtilsModule } from '@tracker/shared-utils';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { EffectsModule } from '@ngrx/effects';
import { TokenEffects } from './+state/token/token.effects';
import { BearerTokenInterceptor } from './interceptors/bearer-token.interceptor';
import { DeniedResponseInterceptor } from './interceptors/denied-response.interceptor';
import { idleReducer } from './+state/idle/idle.reducer';
import { IdleEffects } from './+state/idle/idle.effects';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { CountdownModalComponent } from './modals/countdown-modal/countdown-modal.component';
import { CountdownPanelComponent } from './components/countdown-panel/countdown-panel.component';
import { AUTH_CONFIG_TOKEN, AuthModuleConfig } from './auth.config';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    SharedUtilsModule,
    StoreModule.forFeature('auth', {
      token: tokenReducer,
      idle: idleReducer,
    }),
    EffectsModule.forFeature([
      TokenEffects,
      IdleEffects,
    ]),
    NgbModalModule,
  ],
  exports: [
    ReactiveFormsModule,
  ],
  declarations: [LoginPageComponent, LoginFormComponent, CountdownModalComponent, CountdownPanelComponent]
})
export class AuthModule {
  static forRoot(config: AuthModuleConfig): ModuleWithProviders<AuthModule> {
    return {
      ngModule: AuthModule,
      providers: [
        { provide: HTTP_INTERCEPTORS, useClass: BearerTokenInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: DeniedResponseInterceptor, multi: true },
        { provide: AUTH_CONFIG_TOKEN, useValue: config },
      ]
    };
  }
}
