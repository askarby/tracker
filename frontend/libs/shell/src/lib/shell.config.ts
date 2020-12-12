import { InjectionToken } from '@angular/core';

export const SHELL_CONFIG_TOKEN = new InjectionToken<ShellModuleConfig>('Token for ShellModule configuration-object');

export interface NavigationItem {
  /**
   * Path of page that item activates (navigates to).
   */
  path: string;

  /**
   * i18n-key for title of item.
   */
  i18n: string;

  /**
   * Must contain unique id when having children.
   */
  id?: string;

  /**
   * Icon to apply to item.
   */
  icon?: string;

  /**
   * List of children.
   */
  children?: NavigationItem[];
}

/**
 * Site navigation configuration.
 */
export interface SiteNavigationConfig {
  /**
   * Navigation items for `site` part of shell.
   */
  items: NavigationItem[];

  /**
   * Path to login page.
   */
  loginPath: string;
}

/**
 * Navigation structure configuration.
 */
export interface NavigationConfig {
  /**
   * Configuration for site navigation.
   */
  site: SiteNavigationConfig;

  /**
   * Navigation items for `admin` part of shell.
   */
  admin: NavigationItem[];

  /**
   * Navigation items for `footer` part of shell (applies to both `site` as well as `admin`).
   */
  footer: NavigationItem[];
}

/**
 * Social media configuration.
 */
export interface SocialMediaConfig {
  /**
   * Handle for Facebook account.
   */
  facebookHandle?: string;

  /**
   * Handle for Instagram account.
   */
  instagramHandle?: string;

  /**
   * Handle for Twitter account.
   */
  twitterHandle?: string;

  /**
   * Handle for Github account.
   */
  githubHandle?: string;
}

/**
 * Details about the company providing this service.
 */
export interface CompanyDetails {
  /**
   * Name of the company.
   */
  name: string;

  /**
   * URL to the company's website.
   */
  website: string

  socialMedia: SocialMediaConfig;
}

export interface ShellModuleConfig {
  company: CompanyDetails;
  navigation: NavigationConfig;
}
