import { createInitialIdleState, IdleState } from './idle.state';

describe('IdleState', () => {
  let initialState: IdleState;

  beforeEach(() => {
    initialState = createInitialIdleState();
  });

  describe('initial state', () => {
    it('should have null for initial custom timeout (in milliseconds)', () => {
      expect(initialState.customTimeoutMilliseconds).toBeNull();
    });
  });
});
