import { createRoutingFactory, Spectator } from '@ngneat/spectator/jest';
import { AdminNavbarComponent } from './admin-navbar.component';
import { getTranslocoTestModule } from '../../../../testing/transloco-test.module';
import { MockDirective } from 'ng-mocks';
import { CollapseDirective } from 'ngx-bootstrap/collapse';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { BsDropdownMenuDirective } from 'ngx-bootstrap/dropdown';

describe('AdminNavbarComponent', () => {
  const createComponent = createRoutingFactory({
    component: AdminNavbarComponent,
    stubsEnabled: false,
    imports: [
      getTranslocoTestModule(),
    ],
    declarations: [
      MockDirective(CollapseDirective),
      MockDirective(BsDropdownMenuDirective),
    ],
    schemas: [
      NO_ERRORS_SCHEMA,
    ],
  });

  let spectator: Spectator<AdminNavbarComponent>;
  let component: AdminNavbarComponent;

  beforeEach(() => {
    spectator = createComponent();
    component = spectator.component;
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
