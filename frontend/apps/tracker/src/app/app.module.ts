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
import { ShellModule } from '@tracker/shell';
import { TranslocoRootModule } from './transloco/transloco-root.module';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [AppComponent, HomeComponent],
  imports: [
    // Core (Angular) modules
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,

    // NgRx setup
    StoreModule.forRoot({}, {}),
    StoreDevtoolsModule.instrument({
      maxAge: 25,
      logOnly: environment.production
    }),
    EffectsModule.forRoot([]),
    StoreRouterConnectingModule.forRoot(),

    // Bootstrap
    CollapseModule.forRoot(),
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
    TooltipModule.forRoot(),
    ProgressbarModule.forRoot(),

    // Other 3rd party modules
    TranslocoRootModule,

    // Application specific modules
    AppRouterModule,
    ShellModule.forRoot({
      company: {
        name: 'InnoTech Solutions ApS',
        website: 'https://inno-tech.dk',
        socialMedia: {
          facebookHandle: 'tracker',
          instagramHandle: 'tracker',
          twitterHandle: 'tracker',
          githubHandle: 'tracker',
        }
      },
      navigation: {
        admin: [
          {
            path: '/app/dashboard',
            i18n: 'app.navigation.admin.dashboard',
            icon: 'ni-shop text-primary'
          }
        ],
        site: {
          items: [
            {
              path: '/site/home',
              i18n: 'app.navigation.site.home',
            },
            {
              path: '/site/functionality',
              i18n: 'app.navigation.site.functionality',
            },
            {
              path: '/site/pricing',
              i18n: 'app.navigation.site.pricing',
            }
          ],
          loginPath: '/site/auth/login'
        },
        footer: [
          {
            path: '/site/about-us',
            i18n: 'app.navigation.footer.about-us',
          },
          {
            path: '/site/terms-of-usage',
            i18n: 'app.navigation.footer.terms-of-usage',
          },
          {
            path: '/site/licensing',
            i18n: 'app.navigation.footer.licensing',
          }
        ],
      }
    }),
    AuthModule.forRoot({
      idleCountdownMilliseconds: environment.security.idleCountdownMilliseconds,
      idleTimeoutMilliseconds: environment.security.idleTimeoutMilliseconds,
      urls: {
        acquireToken: `${environment.apiBaseUrl}/login`,
        refreshToken: `${environment.apiBaseUrl}/oauth/access_token`,
      }
    }),
    SharedUtilsModule.forRoot(),
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
