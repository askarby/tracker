import { IdleSelectors } from './idle.selectors';
import { IdleState } from './idle.state';
import { createIdleState } from '../../../testing/ngrx/idle-state.test-data';

describe('Idle Selectors', () => {
  let state: IdleState;

  beforeEach(() => {
    state = createIdleState();
  })

  describe('selectCustomTimeoutMilliseconds', () => {
    it('should select custom timeout (in milliseconds)', () => {
      const selected = IdleSelectors.selectCustomTimeoutMilliseconds.projector(state);
      expect(selected).toEqual(state.customTimeoutMilliseconds);
    });
  });
});
