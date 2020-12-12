import merge from 'ts-deepmerge';
import {
  CompanyDetails,
  NavigationConfig,
  NavigationItem,
  ShellModuleConfig,
  SiteNavigationConfig,
  SocialMediaConfig
} from '../lib/shell.config';

export function createNavigationItem(override: Partial<NavigationItem> = {}): NavigationItem {
  // Be aware, this item contains everything, even though "path"- and "id"-properties should be
  // mutually exclusive, similarly to "children"- and "path"-properties
  const defaultItem: NavigationItem = {
    path: '/path/a',
    i18n: 'i18n-a',
    icon: 'icon-a',
    id: 'id-a',
    children: [
      {
        path: '/path/b',
        i18n: 'i18n-b',
        icon: 'icon-b',
        id: 'id-b',
        children: []
      }
    ]
  };
  return merge(defaultItem, override);
}

export function createSiteNavigationConfig(override: Partial<SiteNavigationConfig> = {}): SiteNavigationConfig {
  const defaultConfig: SiteNavigationConfig = {
    loginPath: '/path/to/login',
    items: [
      createNavigationItem({ i18n: 'site-i18n', path: 'site/path'}),
      createNavigationItem({
        id: 'site-menu',
        i18n: 'site-menu-i18n',
        children: [
          { i18n: 'site-i18n-a', path: 'site/path/a'},
          { i18n: 'site-i18n-b', path: 'site/path/b'}
        ]
      })
    ]
  };
  return merge(defaultConfig, override);
}

export function createNavigationConfig(override: Partial<NavigationConfig> = {}): NavigationConfig {
  const defaultConfig: NavigationConfig = {
    site: createSiteNavigationConfig(),
    admin: [
      createNavigationItem({ i18n: 'admin-i18n', path: 'admin/path'}),
      createNavigationItem({
        id: 'admin-menu',
        i18n: 'admin-menu-i18n',
        children: [
          { i18n: 'admin-i18n-a', path: 'admin/path/a'},
          { i18n: 'admin-i18n-b', path: 'admin/path/b'}
        ]
      })
    ],
    footer: [
      createNavigationItem({ i18n: 'footer-i18n', path: 'footer/path'}),
    ]
  };
  return merge(defaultConfig, override);
}

export function createSocialMediaConfig(override: Partial<SocialMediaConfig> = {}): SocialMediaConfig {
  const defaultConfig: SocialMediaConfig = {
    facebookHandle: 'acme-facebook',
    instagramHandle: 'acme-instagram',
    twitterHandle: 'acme-twitter',
    githubHandle: 'acme-github'
  };
  return merge(defaultConfig, override);
}

export function createCompanyDetails(override: Partial<CompanyDetails> = {}): CompanyDetails {
  const defaultDetails: CompanyDetails = {
    name: 'Acme Inc.',
    website: 'https://acme.com',
    socialMedia: createSocialMediaConfig(),
  };
  return merge(defaultDetails, override);
}

export function createShellModuleConfig(override: Partial<ShellModuleConfig> = {}): ShellModuleConfig {
  const defaultConfig: ShellModuleConfig = {
    navigation: createNavigationConfig(),
    company: createCompanyDetails()
  };
  return merge(defaultConfig, override);
}
