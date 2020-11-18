import { createSelector } from '@ngrx/store';
import { AuthSelectors } from '../auth.selectors';

export namespace IdleSelectors {
  const selectIdleState = createSelector(
    AuthSelectors.selectAuthState,
    (auth) => auth.idle
  );

  /**
   * Retrieves the time that the must pass before having timed out (automatically logging out).
   */
  export const selectTimeout = createSelector(
    selectIdleState,
    (state) => state.timeout
  );
}
