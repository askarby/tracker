import { createAction, props } from '@ngrx/store';

export namespace IdleActions {
  export const setTimeout = createAction(
    '[auth/idle] Set Timeout',
    props<{ timeoutMinutes: number }>()
  );
}
