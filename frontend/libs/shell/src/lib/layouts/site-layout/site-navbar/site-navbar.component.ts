import { Component, Input, OnInit } from '@angular/core';
import { SiteLink } from '../../../models/site-top-navigation.model';
import { SocialMediaConfig } from '../../../shell.config';

@Component({
  selector: 'shell-site-navbar',
  templateUrl: './site-navbar.component.html',
  styleUrls: ['./site-navbar.component.scss']
})
export class SiteNavbarComponent {
  @Input()
  items: SiteLink[];

  @Input()
  loginPath: string;

  @Input()
  socialMedia: SocialMediaConfig;

  isCollapsed = true;
}
