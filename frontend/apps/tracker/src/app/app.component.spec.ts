import { AppComponent } from './app.component';
import { createComponentFactory, Spectator } from '@ngneat/spectator/jest';
import { RouterOutlet } from '@angular/router';
import { MockComponents } from 'ng-mocks';
import { provideMockStore } from '@ngrx/store/testing';

describe('AppComponent', () => {
  const createComponent = createComponentFactory({
    component: AppComponent,
    declarations: [
      MockComponents(RouterOutlet)
    ],
    providers: [
      provideMockStore()
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
