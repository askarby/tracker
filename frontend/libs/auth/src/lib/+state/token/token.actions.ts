import { createAction, props } from '@ngrx/store';
import { AuthSuccess } from '../../models/auth-success.model';

export const TokenActions = {
  loginSuccess: createAction(
    '[auth/token] Login Success',
    props<{ data: AuthSuccess }>()
  ),

  loginFailed: createAction(
    '[auth/token] Login Failed',
    props<{ error: any }>(),
  ),

  refresh: createAction(
    '[auth/token] Refresh Access Token',
  ),

  refreshSuccess: createAction(
    '[auth/token] Refresh Access Token Success',
    props<{ data: AuthSuccess }>()
  ),

  refreshFailed: createAction(
    '[auth/token] Refresh Access Token Failed',
    props<{ error: any }>(),
  ),

  logoutSuccess: createAction(
    '[auth/token] Logout Success',
  ),

  logoutFailed: createAction(
    '[auth/token] Logout Failed',
    props<{ error: any }>(),
  ),
}
