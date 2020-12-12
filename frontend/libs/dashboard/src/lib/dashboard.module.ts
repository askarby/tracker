import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Route, RouterModule } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AdminLayoutComponent, ShellModule } from '@tracker/shell';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { TooltipModule } from 'ngx-bootstrap/tooltip';

export const DashboardsRoutes: Route[] = [
  {
    path: 'dashboard',
    component: AdminLayoutComponent,
    children: [
      {
        path: '',
        component: DashboardComponent
      }
    ]
  }
];

@NgModule({
  declarations: [DashboardComponent],
  imports: [
    // Core (Angular) modules
    CommonModule,
    RouterModule.forChild(DashboardsRoutes),

    // Bootstrap
    BsDropdownModule,
    ProgressbarModule,
    TooltipModule,

    // Application specific modules
    ShellModule,
  ],
  exports: [DashboardComponent]
})
export class DashboardModule {
}
