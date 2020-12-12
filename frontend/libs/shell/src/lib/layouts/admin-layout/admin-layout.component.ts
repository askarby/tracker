import { Component, HostListener } from '@angular/core';
import { Store } from '@ngrx/store';
import { NavigationSelectors } from '../../+state/navigation/navigation.selectors';
import { Observable } from 'rxjs';
import { AdminSidebarLink, AdminSidebarMenu } from '../../models/admin-sidebar-navigation.model';
import { NavigationActions } from '../../+state/navigation/navigation.actions';
import { FooterLink } from '../../models/footer-navigation.model';

@Component({
  selector: 'shell-admin-layout',
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.scss']
})
export class AdminLayoutComponent {
  sidebar$: Observable<(AdminSidebarLink | AdminSidebarMenu)[]>;
  footer$: Observable<FooterLink[]>;

  isMobileResolution!: boolean;

  constructor(private store: Store<any>) {
    this.determineResolution();

    this.sidebar$ = this.store.select(NavigationSelectors.selectSidebarHierarchy);
    this.footer$ = this.store.select(NavigationSelectors.selectFooterList);
  }

  toggleMenu(menu: AdminSidebarMenu) {
    this.store.dispatch(NavigationActions.toggleSidebarMenu({ menu }));
  }

  @HostListener('window:resize', ['$event'])
  isMobile(event: Event) {
    this.determineResolution();
  }

  private determineResolution() {
    this.isMobileResolution = window.innerWidth < 1200;
  }
}
