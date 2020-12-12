import merge from 'ts-deepmerge';
import { FooterLink } from '../../lib/models/footer-navigation.model';

export function createFooterLink(override: Partial<FooterLink> = {}): FooterLink {
  const defaultLink: FooterLink = {
    i18n: 'default-i18n',
    path: 'default/path',
  };
  return merge(defaultLink, override);
}
