import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WINDOW_TOKEN } from './tokens/window.token';
import { ReadableTimePipe } from './pipes/readable-time.pipe';

@NgModule({
  imports: [CommonModule],
  declarations: [ReadableTimePipe],
  exports: [ReadableTimePipe]
})
export class SharedUtilsModule {
  static forRoot(): ModuleWithProviders<SharedUtilsModule> {
    return {
      ngModule: SharedUtilsModule,
      providers: [
        {
          provide: WINDOW_TOKEN,
          useValue: window
        }
      ]
    }
  }
}
