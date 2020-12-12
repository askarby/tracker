import { Spectator, createRoutingFactory } from '@ngneat/spectator/jest';

import { SiteNavbarComponent } from './site-navbar.component';
import { MockDirective } from 'ng-mocks';
import { CollapseDirective } from 'ngx-bootstrap/collapse';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { getTranslocoTestModule } from '../../../../testing/transloco-test.module';
import { createSocialMediaConfig } from '../../../../testing/shell-config.test-data';

describe('SiteNavbarComponent', () => {
  const createComponent = createRoutingFactory({
    component: SiteNavbarComponent,
    imports: [
      getTranslocoTestModule(),
    ],
    declarations: [
      MockDirective(CollapseDirective),
    ],
    schemas: [
      NO_ERRORS_SCHEMA,
    ],
  });

  let spectator: Spectator<SiteNavbarComponent>;
  let component: SiteNavbarComponent;

  beforeEach(() => {
    spectator = createComponent({
      props: {
        items: [],
        loginPath: '/login/path',
        socialMedia: createSocialMediaConfig(),
      }
    });
    component = spectator.component;
  });

  it('should be created', () => {
    expect(spectator.component).toBeTruthy();
  });
});
