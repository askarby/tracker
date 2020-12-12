import { ChangeDetectionStrategy, Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { NavigationSelectors } from '../../+state/navigation/navigation.selectors';
import { Observable } from 'rxjs';
import { SiteLink } from '../../models/site-top-navigation.model';
import { FooterLink } from '../../models/footer-navigation.model';
import { SHELL_CONFIG_TOKEN, ShellModuleConfig } from '../../shell.config';

@Component({
  selector: 'shell-site-layout',
  templateUrl: './site-layout.component.html',
  styleUrls: ['./site-layout.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SiteLayoutComponent implements OnInit, OnDestroy {
  topNavigation$: Observable<SiteLink[]>;
  footer$: Observable<FooterLink[]>;

  constructor(
    @Inject(SHELL_CONFIG_TOKEN) public config: ShellModuleConfig,
    private store: Store<any>) {

    this.topNavigation$ = this.store.select(NavigationSelectors.selectSiteTopBarList);
    this.footer$ = this.store.select(NavigationSelectors.selectFooterList);
  }

  ngOnInit() {
    const body = document.getElementsByTagName('body')[0];
    body.classList.add('bg-default');
  }

  ngOnDestroy() {
    const body = document.getElementsByTagName('body')[0];
    body.classList.remove('bg-default');
  }
}
