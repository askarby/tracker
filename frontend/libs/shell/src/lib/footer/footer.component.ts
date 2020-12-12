import { Component, Inject, Input, OnChanges, SimpleChanges } from '@angular/core';
import { SHELL_CONFIG_TOKEN, ShellModuleConfig } from '../shell.config';
import { FooterLink } from '../models/footer-navigation.model';

@Component({
  selector: 'shell-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnChanges {
  @Input()
  displayFor: 'site' | 'admin' = 'admin';

  @Input()
  items!: FooterLink[];

  now = new Date();

  constructor(@Inject(SHELL_CONFIG_TOKEN) public config: ShellModuleConfig) {
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log('FooterComponent::ngOnChanges', changes);
  }
}
