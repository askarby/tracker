import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'tracker-countdown-panel',
  templateUrl: './countdown-panel.component.html',
  styleUrls: ['./countdown-panel.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CountdownPanelComponent {
  @Input()
  remainingSeconds!: number;
}
