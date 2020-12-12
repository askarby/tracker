import { createAction, props } from '@ngrx/store';
import { AdminSidebarMenu } from '../../models/admin-sidebar-navigation.model';
import { NavigationItem } from '../../shell.config';

export const NavigationActions = {
  loadNavigation: createAction('[shell/navigation] Load Navigation'),

  navigationLoaded: createAction(
    '[shell/navigation] Navigation Loaded',
    props<{
      site: NavigationItem[],
      admin: NavigationItem[],
      footer: NavigationItem[]
    }>()
  ),

  toggleSidebarMenu: createAction(
    '[shell/navigation] Toggle Sidebar Menu',
    props<{ menu: AdminSidebarMenu }>()
  )
};
