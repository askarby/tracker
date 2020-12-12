import { createMutableReducer, mutableOn } from 'ngrx-etc';
import { NavigationActions } from './navigation.actions';
import { createInitialNavigationState, NavigationMenuExpansion, NavigationState } from './navigation.state';

export const navigationReducer = createMutableReducer<NavigationState>(
  createInitialNavigationState(),
  mutableOn(NavigationActions.navigationLoaded, (draft, { site, admin, footer }) => {
    draft.site = site;
    draft.admin = admin;
    draft.footer = footer;
  }),
  mutableOn(NavigationActions.toggleSidebarMenu, (draft, { menu }) => {
    const id = menu.id;
    const newState = menu.isCollapsed ? NavigationMenuExpansion.EXPANDED : NavigationMenuExpansion.COLLAPSED;
    draft.sidebarExpansion[id] = newState;
  })
);
