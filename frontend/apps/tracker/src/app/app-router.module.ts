import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { authRoutes } from '@tracker/auth';

@NgModule({
  imports: [
    RouterModule.forRoot([
      {
        path: '',
        component: HomeComponent
      },
      {
        path: 'auth',
        children: [
          ...authRoutes,
        ]
      },
    ]),
  ],
  exports: [
    RouterModule
  ]
})
export class AppRouterModule {

}
