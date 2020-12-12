import { createSelector } from '@ngrx/store';
import { ShellSelectors } from '../shell.selectors';
import { AdminSidebarItem, AdminSidebarLink, AdminSidebarMenu } from '../../models/admin-sidebar-navigation.model';
import { NavigationMenuExpansion } from './navigation.state';
import { NavigationItem } from '../../shell.config';
import { SiteLink } from '../../models/site-top-navigation.model';

// ---- "Private" selectors to select compositional structure to produce "public" selectors from!

const selectNavigationState = createSelector(
  ShellSelectors.selectShellState,
  (shell) => shell.navigation
);

const selectSidebarNavigationItems = createSelector(
  selectNavigationState,
  (state) => state.admin
);

const selectSiteNavigationItems = createSelector(
  selectNavigationState,
  (state) => state.site
);

const selectFooterNavigationItems = createSelector(
  selectNavigationState,
  (state) => state.footer
);

const selectSidebarExpansionExpansion = createSelector(
  selectNavigationState,
  (state) => state.sidebarExpansion
);

/**
 * Publicly available selectors for Navigation feature state.
 */
export const NavigationSelectors = {
  /**
   * Retrieves the `admin` navigation sidebar items.
   */
  selectSidebarHierarchy: createSelector(
    selectSidebarNavigationItems,
    selectSidebarExpansionExpansion,
    (items, expansion) => {
      return items.map((item) => createAdminSidebarItem(item, expansion));
    }
  ),

  /**
   * Retrieves the `site` navigation top-bar items.
   */
  selectSiteTopBarList: createSelector(
    selectSiteNavigationItems,
    (items) => {
      return items.map<SiteLink>((item) => ({
        path: item.path,
        i18n: item.i18n
      }));
    }
  ),

  /**
   * Retrieves the `footer` navigation items.
   */
  selectFooterList: createSelector(
    selectFooterNavigationItems,
    (items) => {
      return items.map<SiteLink>((item) => ({
        path: item.path,
        i18n: item.i18n
      }));
    }
  ),
};

// ---- Helper functions below!

function createAdminSidebarItem(item: NavigationItem, expansion: { [id: string]: NavigationMenuExpansion }): AdminSidebarItem {
  const hasChildren = (item as any).children;
  if (hasChildren) {
    const nested = item as NavigationItem;
    return createAdminSidebarMenu(nested, expansion);
  } else {
    const flat = item as NavigationItem;
    return createAdminSidebarLink(flat);
  }
}

function createAdminSidebarMenu(item: NavigationItem, expansion: { [id: string]: NavigationMenuExpansion }): AdminSidebarMenu {
  const isCollapsed = expansion[item.id] === NavigationMenuExpansion.COLLAPSED;
  return {
    id: item.id,
    i18n: item.i18n,
    icontype: item.icon,
    isCollapsed,
    children: (item.children || []).map((child) => createAdminSidebarItem(child, expansion))
  };
}

function createAdminSidebarLink(item: NavigationItem): AdminSidebarLink {
  return {
    path: item.path,
    i18n: item.i18n,
    icontype: item.icon,
  }
}
