import { TokenActions } from './token.actions';
import { createAuthSuccess } from '../../testing/models/auth-success-model.test-data';
import { tokenReducer } from './token.reducer';
import { TokenState, TokenStatus } from './token.state';
import { AuthSuccess } from '../../model/auth-success.model';

describe('Token Reducer', () => {
  describe(`reducing action with type "${TokenActions.loginSuccess.type}"`, () => {
    let authSuccess: AuthSuccess;
    let reducedState: TokenState;

    beforeEach(() => {
      authSuccess = createAuthSuccess();

      const action = TokenActions.loginSuccess({ data: authSuccess });
      reducedState = tokenReducer(undefined, action);
    });

    it('should set status', () => {
      expect(reducedState.status).toEqual(TokenStatus.LOGGED_IN);
    });

    it('should update access token', () => {
      expect(reducedState.accessToken).toEqual({
        raw: authSuccess.accessToken,
        username: authSuccess.username,
        expiresAt: authSuccess.parsedAccessToken.exp,
        roles: authSuccess.parsedAccessToken.roles
      });
    });

    it('should update refresh token', () => {
      expect(reducedState.refreshToken).toEqual(authSuccess.refreshToken);
    });

    it('should clear error', () => {
      expect(reducedState.error).toBeNull();
    });
  });

  describe(`reducing action with type "${TokenActions.refreshSuccess.type}"`, () => {
    let authSuccess: AuthSuccess;
    let reducedState: TokenState;

    beforeEach(() => {
      authSuccess = createAuthSuccess();

      const action = TokenActions.refreshSuccess({ data: authSuccess });
      reducedState = tokenReducer(undefined, action);
    });

    it('should update access token', () => {
      expect(reducedState.accessToken).toEqual({
        raw: authSuccess.accessToken,
        username: authSuccess.username,
        expiresAt: authSuccess.parsedAccessToken.exp,
        roles: authSuccess.parsedAccessToken.roles
      });
    });

    it('should update refresh token', () => {
      expect(reducedState.refreshToken).toEqual(authSuccess.refreshToken);
    });

    it('should clear error', () => {
      expect(reducedState.error).toBeNull();
    });
  });

  [
    TokenActions.loginFailed({ error: 'error' }),
    TokenActions.logoutSuccess(),
    TokenActions.refreshFailed({ error: 'error' })
  ].forEach((action) => {
    describe(`reducing action with type "${action.type}"`, () => {
      let reducedState: TokenState;

      beforeEach(() => {
        reducedState = tokenReducer(undefined, action);
      });

      it('should set status', () => {
        expect(reducedState.status).toEqual(TokenStatus.LOGGED_OUT);
      });

      it('should clear access token', () => {
        expect(reducedState.accessToken).toBeNull();
      });

      it('should clear refresh token', () => {
        expect(reducedState.refreshToken).toBeNull();
      });

      it('should set error', () => {
        const error = (action as any).error;
        expect(reducedState.error).toEqual(error);
      });
    });
  });
});
