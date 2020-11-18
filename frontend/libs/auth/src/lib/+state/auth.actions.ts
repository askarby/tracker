import { createAction, props } from '@ngrx/store';

export namespace AuthActions {
  export const initialize = createAction(
    '[auth] Initialize',
  );

  export const login = createAction(
    '[auth] Login',
    props<{ username: string, password: string }>()
  );

  export const logout = createAction(
    '[auth] Logout',
  );
}
