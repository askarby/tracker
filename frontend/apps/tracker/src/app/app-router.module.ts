import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { authRoutes } from '@tracker/auth';
import { AdminLayoutComponent, SiteLayoutComponent } from '@tracker/shell';

@NgModule({
  imports: [
    RouterModule.forRoot([
      {
        path: 'site',
        component: SiteLayoutComponent,
        children: [
          {
            path: 'home',
            component: HomeComponent
          },
          {
            path: 'auth',
            children: [
              ...authRoutes,
            ]
          },
        ]
      },
      {
        path: 'app',
        component: AdminLayoutComponent,
        children: [
          {
            path: 'dashboard',
            loadChildren: () => import('libs/dashboard/src/index').then(m => m.DashboardModule)
          },
          {
            path: 'time',
            loadChildren: () => import('libs/time/src/index').then(m => m.TimeModule)
          },
        ]
      },
      {
        path: '**',
        redirectTo: 'site/home',
      }
    ]),
  ],
  exports: [
    RouterModule
  ]
})
export class AppRouterModule {

}
