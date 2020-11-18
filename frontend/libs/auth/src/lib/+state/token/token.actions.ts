import { createAction, props } from '@ngrx/store';
import { AuthSuccess } from '../../model/auth-success.model';

export namespace TokenActions {
  export const loginSuccess = createAction(
    '[auth/token] Login Success',
    props<{ data: AuthSuccess }>()
  );

  export const loginFailed = createAction(
    '[auth/token] Login Failed',
    props<{ error: any }>(),
  );

  export const refresh = createAction(
    '[auth/token] Refresh Access Token',
  );

  export const refreshSuccess = createAction(
    '[auth/token] Refresh Access Token Success',
    props<{ data: AuthSuccess }>()
  );

  export const refreshFailed = createAction(
    '[auth/token] Refresh Access Token Failed',
    props<{ error: any }>(),
  );

  export const logoutSuccess = createAction(
    '[auth/token] Logout Success',
  );

  export const logoutFailed = createAction(
    '[auth/token] Logout Failed',
    props<{ error: any }>(),
  );
}
