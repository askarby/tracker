import { Spectator, createComponentFactory } from '@ngneat/spectator/jest';

import { CountdownPanelComponent } from './countdown-panel.component';
import { MockPipe } from 'ng-mocks';
import { ReadableTimePipe } from '@tracker/shared-utils';

describe('CountdownPanelComponent', () => {
  const createComponent = createComponentFactory({
    component: CountdownPanelComponent,
    declarations: [
      MockPipe(ReadableTimePipe, (input) => `readable:${input}`)
    ]
  });

  let spectator: Spectator<CountdownPanelComponent>;
  let component: CountdownPanelComponent;

  beforeEach(() => {
    spectator = createComponent();
    component = spectator.component;
  })

  it('should be created', () => {
    expect(spectator.component).toBeTruthy();
  });
});
