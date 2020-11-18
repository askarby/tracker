import { createMutableReducer, mutableOn } from 'ngrx-etc';
import { createInitialIdleState, IdleState } from './idle.state';
import { IdleActions } from './idle.actions';
import { Milliseconds } from '@tracker/shared-utils';

export const idleReducer = createMutableReducer<IdleState>(
  createInitialIdleState(),
  mutableOn(IdleActions.setTimeout, (draft, { timeoutMinutes }) => {
    draft.timeout = Milliseconds.fromMinutes(timeoutMinutes);
  }),
);
