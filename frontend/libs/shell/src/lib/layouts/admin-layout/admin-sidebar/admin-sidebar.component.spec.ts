import { createRoutingFactory, Spectator } from '@ngneat/spectator/jest';
import { AdminSidebarComponent } from './admin-sidebar.component';
import { MockComponents, MockDirective, MockDirectives } from 'ng-mocks';
import { CollapseDirective } from 'ngx-bootstrap/collapse';
import { PerfectScrollbarComponent } from 'ngx-perfect-scrollbar';
import { getTranslocoTestModule } from '../../../../testing/transloco-test.module';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('AdminSidebarComponent', () => {
  const createComponent = createRoutingFactory({
    component: AdminSidebarComponent,
    imports: [
      getTranslocoTestModule(),
    ],
    declarations: [
      MockComponents(PerfectScrollbarComponent),
      MockDirective(CollapseDirective),
    ],
    schemas: [
      NO_ERRORS_SCHEMA,
    ],
    stubsEnabled: false,
  });

  let spectator: Spectator<AdminSidebarComponent>;
  let component: AdminSidebarComponent;

  beforeEach(() => {
    spectator = createComponent({
      props: {
        items: [] // TODO: Create test data, and use here!
      }
    });
    component = spectator.component;
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
