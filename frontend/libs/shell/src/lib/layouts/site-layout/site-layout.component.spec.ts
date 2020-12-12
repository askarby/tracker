import { SiteLayoutComponent } from './site-layout.component';
import { createRoutingFactory, Spectator } from '@ngneat/spectator/jest';
import { SiteNavbarComponent } from './site-navbar/site-navbar.component';
import { FooterComponent } from '../../footer/footer.component';
import { MockComponents } from 'ng-mocks';
import { SHELL_CONFIG_TOKEN } from '../../shell.config';
import { createShellModuleConfig } from '../../../testing/shell-config.test-data';
import { provideMockStore } from '@ngrx/store/testing';
import { NavigationSelectors } from '../../+state/navigation/navigation.selectors';

describe('SiteLayoutComponent', () => {
  const createComponent = createRoutingFactory({
    component: SiteLayoutComponent,
    declarations: [
      MockComponents(SiteNavbarComponent, FooterComponent)
    ],
    providers: [
      {
        provide: SHELL_CONFIG_TOKEN,
        useValue: createShellModuleConfig()
      },
      provideMockStore({
        selectors: [
          {
            selector: NavigationSelectors.selectSiteTopBarList,
            value: [],
          },
          {
            selector: NavigationSelectors.selectFooterList,
            value: [],
          }
        ]
      })
    ],
    stubsEnabled: false
  });

  let spectator: Spectator<SiteLayoutComponent>;
  let component: SiteLayoutComponent;

  beforeEach(() => {
    spectator = createComponent();
    component = spectator.component;
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
