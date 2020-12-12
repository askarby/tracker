import { AdminLayoutComponent } from './admin-layout.component';
import { createComponentFactory, Spectator } from '@ngneat/spectator/jest';
import { MockComponents } from 'ng-mocks';
import { AdminSidebarComponent } from './admin-sidebar/admin-sidebar.component';
import { AdminNavbarComponent } from './admin-navbar/admin-navbar.component';
import { FooterComponent } from '../../footer/footer.component';
import { RouterOutlet } from '@angular/router';
import { provideMockStore } from '@ngrx/store/testing';
import { NavigationSelectors } from '../../+state/navigation/navigation.selectors';
import {
  createAdminSidebarLink,
  createAdminSidebarMenu
} from '../../../testing/models/admin-sidebar-navigation-model.test-data';

describe('AdminLayoutComponent', () => {
  const createComponent = createComponentFactory({
    component: AdminLayoutComponent,
    declarations: [
      MockComponents(AdminSidebarComponent, AdminNavbarComponent, FooterComponent, RouterOutlet)
    ],
    providers: [
      provideMockStore({
        selectors: [
          {
            selector: NavigationSelectors.selectSidebarHierarchy,
            value: [
              createAdminSidebarLink({ path: 'a', i18n: 'i18n/a' }),
              createAdminSidebarMenu({
                id: 'b',
                i18n: 'i18n/b',
                children: [
                  createAdminSidebarLink({ path: 'c', i18n: 'i18n/c' }),
                ]
              })
            ]
          }
        ]
      })
    ]
  });

  let spectator: Spectator<AdminLayoutComponent>;
  let component: AdminLayoutComponent;

  beforeEach(() => {
    spectator = createComponent();
    component = spectator.component;
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
