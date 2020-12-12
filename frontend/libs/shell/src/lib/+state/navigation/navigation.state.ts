import { NavigationItem } from '../../shell.config';

export enum NavigationMenuExpansion {
  EXPANDED = 'expanded',
  COLLAPSED = 'collapsed'
}

export interface NavigationState {
  /**
   * Navigation items for `site` part of shell.
   */
  site: NavigationItem[];

  /**
   * Navigation items for `admin` part of shell.
   */
  admin: NavigationItem[];

  /**
   * Navigation items for `footer` part of shell (applies to both `site` as well as `admin`).
   */
  footer: NavigationItem[];

  /**
   * Expansion state of sidebar menus.
   *
   * Contains an object (essentially a map) of ids of `AdminSidebarSubmenu` objects,
   * paired with a `NavigationMenuExpansion`.
   */
  sidebarExpansion: { [id: string]: NavigationMenuExpansion };
}

export function createInitialNavigationState(): NavigationState {
  return {
    site: [],
    admin: [],
    footer: [],
    sidebarExpansion: { },
  };
}
