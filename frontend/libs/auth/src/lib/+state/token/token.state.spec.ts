import { TokenState, createInitialTokenState } from './token.state';

describe('TokenState', () => {
  describe('createInitialTokenState', () => {
    let initialState: TokenState;

    beforeEach(() => {
      initialState = createInitialTokenState();
    });

    it('should have no access token', () => {
      expect(initialState.accessToken).toBeNull();
    });

    it('should have no refresh token', () => {
      expect(initialState.refreshToken).toBeNull();
    });
  });
});
