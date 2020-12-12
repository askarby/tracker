import { Component, ElementRef, OnInit } from '@angular/core';
import { Event, NavigationEnd, NavigationError, NavigationStart, Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'shell-admin-navbar',
  templateUrl: './admin-navbar.component.html',
  styleUrls: ['./admin-navbar.component.scss']
})
export class AdminNavbarComponent {
  public focus: boolean;

  public location: Location;

  sidenavOpen = true;

  constructor(
    location: Location,
    private element: ElementRef,
    private router: Router
  ) {
    this.focus = false;
    this.location = location;
    this.router.events.subscribe((event: Event) => {
       if (event instanceof NavigationStart) {
           // Show loading indicator

       }
       if (event instanceof NavigationEnd) {
           // Hide loading indicator
           if (window.innerWidth < 1200) {
             document.body.classList.remove('g-sidenav-pinned');
             document.body.classList.add('g-sidenav-hidden');
             this.sidenavOpen = false;
           }
       }

       if (event instanceof NavigationError) {
           // Hide loading indicator

           // Present error to user
           console.log(event.error);
       }
   });

  }

  openSearch() {
    document.body.classList.add('g-navbar-search-showing');
    setTimeout(function() {
      document.body.classList.remove('g-navbar-search-showing');
      document.body.classList.add('g-navbar-search-show');
    }, 150);
    setTimeout(function() {
      document.body.classList.add('g-navbar-search-shown');
    }, 300);
  }
  closeSearch() {
    document.body.classList.remove('g-navbar-search-shown');
    setTimeout(function() {
      document.body.classList.remove('g-navbar-search-show');
      document.body.classList.add('g-navbar-search-hiding');
    }, 150);
    setTimeout(function() {
      document.body.classList.remove('g-navbar-search-hiding');
      document.body.classList.add('g-navbar-search-hidden');
    }, 300);
    setTimeout(function() {
      document.body.classList.remove('g-navbar-search-hidden');
    }, 500);
  }
  openSidebar() {
    if (document.body.classList.contains('g-sidenav-pinned')) {
      document.body.classList.remove('g-sidenav-pinned');
      document.body.classList.add('g-sidenav-hidden');
      this.sidenavOpen = false;
    } else {
      document.body.classList.add('g-sidenav-pinned');
      document.body.classList.remove('g-sidenav-hidden');
      this.sidenavOpen = true;
    }
  }
  toggleSidenav() {
    if (document.body.classList.contains('g-sidenav-pinned')) {
      document.body.classList.remove('g-sidenav-pinned');
      document.body.classList.add('g-sidenav-hidden');
      this.sidenavOpen = false;
    } else {
      document.body.classList.add('g-sidenav-pinned');
      document.body.classList.remove('g-sidenav-hidden');
      this.sidenavOpen = true;
    }
  }
}
