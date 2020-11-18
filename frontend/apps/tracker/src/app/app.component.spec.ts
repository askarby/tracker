import { AppComponent } from './app.component';
import { createComponentFactory, Spectator } from '@ngneat/spectator/jest';
import { RouterOutlet } from '@angular/router';
import { MockComponents } from 'ng-mocks';

describe('AppComponent', () => {
  const createComponent = createComponentFactory({
    component: AppComponent,
    declarations: [
      MockComponents(RouterOutlet)
    ]
  });

  let spectator: Spectator<AppComponent>;
  let component: AppComponent;

  beforeEach(() => {
    spectator = createComponent();
    component = spectator.component;
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
