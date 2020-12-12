import { Spectator, createComponentFactory } from '@ngneat/spectator/jest';

import { TimeOverviewPageComponent } from './time-overview-page.component';

describe('TimeOverviewPageComponent', () => {
  let spectator: Spectator<TimeOverviewPageComponent>;
  const createComponent = createComponentFactory(TimeOverviewPageComponent);

  it('should create', () => {
    spectator = createComponent();

    expect(spectator.component).toBeTruthy();
  });
});
