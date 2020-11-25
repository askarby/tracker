import { IdleActions } from './idle.actions';
import { idleReducer } from './idle.reducer';

describe('Idle Reducer', () => {
  describe(`reducing action with type "${IdleActions.setTimeout.type}"`, () => {
    it('should set customTimeoutMilliseconds, taking in minutes value, setting milliseconds value', () => {
      const action = IdleActions.setTimeout({ timeoutMinutes: 5 });
      const reducedState = idleReducer(undefined, action);
      expect(reducedState.customTimeoutMilliseconds).toEqual(5 * 60 * 1000);
    });
  });
});
