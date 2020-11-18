import { Spectator, createComponentFactory } from '@ngneat/spectator';

import { HomeComponent } from './home.component';

describe('HomeComponent', () => {
  const createComponent = createComponentFactory({
    component: HomeComponent
  });

  let spectator: Spectator<HomeComponent>;
  let component: HomeComponent;

  beforeEach(() => {
    spectator = createComponent();
    component = spectator.component;
  })

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
