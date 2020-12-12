import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Route } from '@angular/router';
import { TimeOverviewPageComponent } from './pages/time-overview-page/time-overview-page.component';

export const TimeRoutes: Route[] = [
  {
    path: '',
    component: TimeOverviewPageComponent
  }
];

@NgModule({
  imports: [
    // Core (Angular) modules
    CommonModule,
    RouterModule.forChild(TimeRoutes),
  ],
  declarations: [
    TimeOverviewPageComponent
  ],
})
export class TimeModule {}
