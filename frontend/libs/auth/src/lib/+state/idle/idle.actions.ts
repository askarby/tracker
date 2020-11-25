import { createAction, props } from '@ngrx/store';

export const IdleActions = {
  setTimeout: createAction(
    '[auth/idle] Set Timeout',
    props<{ timeoutMinutes: number }>()
  )
}
