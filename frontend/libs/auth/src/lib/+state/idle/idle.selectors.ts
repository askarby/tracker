import { createSelector } from '@ngrx/store';
import { AuthSelectors } from '../auth.selectors';

const selectIdleState = createSelector(
  AuthSelectors.selectAuthState,
  (auth) => auth.idle
);

export const IdleSelectors = {
  /**
   * Retrieves the time (custommized)  that the must pass before having timed out (automatically logging out).
   */
  selectCustomTimeoutMilliseconds: createSelector(
    selectIdleState,
    (state) => state.customTimeoutMilliseconds
  )
}
