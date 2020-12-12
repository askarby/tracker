import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { SiteLayoutComponent } from './layouts/site-layout/site-layout.component';
import { RouterModule } from '@angular/router';
import { FooterComponent } from './footer/footer.component';
import { AdminNavbarComponent } from './layouts/admin-layout/admin-navbar/admin-navbar.component';
import { AdminSidebarComponent } from './layouts/admin-layout/admin-sidebar/admin-sidebar.component';
import {
  PERFECT_SCROLLBAR_CONFIG,
  PerfectScrollbarConfigInterface,
  PerfectScrollbarModule
} from 'ngx-perfect-scrollbar';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { SHELL_CONFIG_TOKEN, ShellModuleConfig } from './shell.config';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { StoreModule } from '@ngrx/store';
import { navigationReducer } from './+state/navigation/navigation.reducer';
import { TRANSLOCO_SCOPE, TranslocoModule } from '@ngneat/transloco';
import { EffectsModule } from '@ngrx/effects';
import { NavigationEffects } from './+state/navigation/navigation.effects';
import { SiteNavbarComponent } from './layouts/site-layout/site-navbar/site-navbar.component';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

export const loader = ['da', 'en'].reduce((acc, lang) => {
  acc[lang] = () => import(`../assets/i18n/${lang}.json`);
  return acc;
}, {});

@NgModule({
  imports: [
    // Core (Angular) modules
    CommonModule,
    RouterModule,

    // NgRx setup
    StoreModule.forFeature('shell', {
      navigation: navigationReducer,
    }),
    EffectsModule.forFeature([
      NavigationEffects
    ]),

    // Bootstrap
    CollapseModule,
    TooltipModule,

    // Other 3rd party modules
    PerfectScrollbarModule,
    TranslocoModule,
  ],
  declarations: [
    AdminLayoutComponent,
    AdminNavbarComponent,
    AdminSidebarComponent,
    FooterComponent,
    SiteLayoutComponent,
    SiteNavbarComponent,
  ],
  exports: [
    AdminLayoutComponent,
    SiteLayoutComponent
  ]
})
export class ShellModule {
  static forRoot(config: ShellModuleConfig): ModuleWithProviders<ShellModule> {
    return {
      ngModule: ShellModule,
      providers: [
        {
          provide: SHELL_CONFIG_TOKEN,
          useValue: config,
        },
        {
          provide: PERFECT_SCROLLBAR_CONFIG,
          useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
        },
        {
          provide: TRANSLOCO_SCOPE,
          useValue: {
            scope: 'shell',
            loader
          }
        }
      ]
    };
  }
}
