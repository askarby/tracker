import { createAction, props } from '@ngrx/store';

export const AuthActions = {
  initialize: createAction(
    '[auth] Initialize',
  ),

  login: createAction(
    '[auth] Login',
    props<{ username: string, password: string }>()
  ),

  logout: createAction(
    '[auth] Logout',
  ),
}
