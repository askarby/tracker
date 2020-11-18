import { Spectator, createComponentFactory } from '@ngneat/spectator';

import { CountdownPanelComponent } from './countdown-panel.component';
import { MockPipe } from 'ng-mocks';
import { ReadableTimePipe } from '../../../../../shared-utils/src/lib/pipes/readable-time.pipe';

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
