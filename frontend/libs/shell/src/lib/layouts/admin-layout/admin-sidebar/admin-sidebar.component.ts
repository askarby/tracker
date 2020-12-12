import { Component, Input, OnInit, Output, EventEmitter, OnChanges, SimpleChanges, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { AdminSidebarLink, AdminSidebarMenu } from '../../../models/admin-sidebar-navigation.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

const misc: any = {
  sidebar_mini_active: true
};

@Component({
  selector: 'shell-admin-sidebar',
  templateUrl: './admin-sidebar.component.html',
  styleUrls: ['./admin-sidebar.component.scss']
})
export class AdminSidebarComponent implements OnInit, OnDestroy {
  @Input()
  items: (AdminSidebarLink | AdminSidebarMenu)[];

  @Output()
  toggleMenu = new EventEmitter<AdminSidebarMenu>();

  isCollapsed = true;

  private readonly onDestroy = new Subject();

  constructor(private router: Router) {

  }

  ngOnInit() {
    this.router.events.pipe(
      takeUntil(this.onDestroy.asObservable())
    ).subscribe(() => {
      this.isCollapsed = true;
    });
  }

  ngOnDestroy() {
    this.onDestroy.next();
    this.onDestroy.complete();
  }

  onMouseEnterSidenav() {
    if (!document.body.classList.contains('g-sidenav-pinned')) {
      document.body.classList.add('g-sidenav-show');
    }
  }

  onMouseLeaveSidenav() {
    if (!document.body.classList.contains('g-sidenav-pinned')) {
      document.body.classList.remove('g-sidenav-show');
    }
  }

  minimizeSidebar() {
    const sidenavToggler = document.getElementsByClassName(
      'sidenav-toggler'
    )[0];
    const body = document.getElementsByTagName('body')[0];
    if (body.classList.contains('g-sidenav-pinned')) {
      misc.sidebar_mini_active = true;
    } else {
      misc.sidebar_mini_active = false;
    }
    if (misc.sidebar_mini_active === true) {
      body.classList.remove('g-sidenav-pinned');
      body.classList.add('g-sidenav-hidden');
      sidenavToggler.classList.remove('active');
      misc.sidebar_mini_active = false;
    } else {
      body.classList.add('g-sidenav-pinned');
      body.classList.remove('g-sidenav-hidden');
      sidenavToggler.classList.add('active');
      misc.sidebar_mini_active = true;
    }
  }
}
