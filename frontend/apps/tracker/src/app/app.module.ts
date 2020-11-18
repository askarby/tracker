import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { environment } from '@environment';
import { EffectsModule } from '@ngrx/effects';
import { StoreRouterConnectingModule } from '@ngrx/router-store';
import { HomeComponent } from './home/home.component';
import { SharedUtilsModule } from '@tracker/shared-utils';
import { AppRouterModule } from './app-router.module';
import { AuthModule } from '@tracker/auth';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [AppComponent, HomeComponent],
  imports: [
    // Core (Angular) modules
    BrowserModule,
    HttpClientModule,

    // NgRx setup
    StoreModule.forRoot({}, {}),
    StoreDevtoolsModule.instrument({
      maxAge: 25,
      logOnly: environment.production
    }),
    EffectsModule.forRoot([]),
    StoreRouterConnectingModule.forRoot(),

    // Application specific modules
    AppRouterModule,
    AuthModule.forRoot({
      idleCountdownMilliseconds: environment.security.idleCountdownMilliseconds,
      idleTimeoutMilliseconds: environment.security.idleTimeoutMilliseconds,
      urls: {
        acquireToken: `${environment.apiBaseUrl}/login`,
        refreshToken: `${environment.apiBaseUrl}/oauth/access_token`,
      }
    }),
    SharedUtilsModule.forRoot(),
    NgbModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
