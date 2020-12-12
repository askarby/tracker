import merge from 'ts-deepmerge';
import { AdminSidebarLink, AdminSidebarMenu } from '../../lib/models/admin-sidebar-navigation.model';

export function createAdminSidebarLink(override: Partial<AdminSidebarLink> = {}): AdminSidebarLink {
  const defaultLink: AdminSidebarLink = {
    i18n: 'default-i18n',
    path: 'default/path',
    icontype: 'default-icontype'
  };
  return merge(defaultLink, override);
}

export function createAdminSidebarMenu(override: Partial<AdminSidebarMenu> = {}): AdminSidebarMenu {
  const defaultMenu: AdminSidebarMenu = {
    id: 'default-id',
    i18n: 'default-i18n',
    icontype: 'default-icontype',
    isCollapsed: true,
    children: [],
  };
  return merge(defaultMenu, override);
}
