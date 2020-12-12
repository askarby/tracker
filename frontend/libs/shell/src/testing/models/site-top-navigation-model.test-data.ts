import merge from 'ts-deepmerge';
import { FooterLink } from '../../lib/models/footer-navigation.model';
import { SiteLink } from '../../lib/models/site-top-navigation.model';

export function createSiteLink(override: Partial<SiteLink> = {}): SiteLink {
  const defaultLink: SiteLink = {
    i18n: 'default-i18n',
    path: 'default/path',
  };
  return merge(defaultLink, override);
}
