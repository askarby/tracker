export interface AdminSidebarLink {
  path: string;
  i18n: string;
  icontype?: string;
}

export interface AdminSidebarMenu {
  id: string;
  i18n: string;
  icontype?: string;
  isCollapsed: boolean;
  children: AdminSidebarItem[];
}

export type AdminSidebarItem = AdminSidebarLink | AdminSidebarMenu;
