import { TokenState, TokenStatus } from './token.state';
import { createAccessToken, createTokenState } from '../../testing/ngrx/token-state.test-data';
import { TokenSelectors } from './token.selectors';
import { enumValuesOf } from '../../testing/enum.test-utils';

describe('Token Selectors', () => {
  let state: TokenState;

  beforeEach(() => {
    state = createTokenState();
  });

  describe('selectAccessToken', () => {
    it('should select raw access token (when available) ', () => {
      const selected = TokenSelectors.selectAccessToken.projector(state);
      expect(selected).toEqual(state.accessToken?.raw);
    });


    [null, undefined].forEach((absent) => {
      it(`should select null when access token is absent (it being ${absent})`, () => {
        state = createTokenState({
          accessToken: absent
        });
        const selected = TokenSelectors.selectAccessToken.projector(state);
        expect(selected).toBeNull();
      });
    });

    it('should select null when raw access token is undefined', () => {
      state = createTokenState({
        accessToken: createAccessToken({
          raw: undefined
        })
      });
      const selected = TokenSelectors.selectAccessToken.projector(state);
      expect(selected).toBeNull();
    });
  });

  describe('selectAccessTokenExpiration', () => {
    it('should select expiration from access token (when available), converted from seconds to milliseconds', () => {
      const selected = TokenSelectors.selectAccessTokenExpiration.projector(state);
      // @ts-ignore
      expect(selected).toEqual(state.accessToken?.expiresAt * 1000);
    });


    [null, undefined].forEach((absent) => {
      it(`should select null when access token is absent (it being ${absent})`, () => {
        state = createTokenState({
          accessToken: absent
        });
        const selected = TokenSelectors.selectAccessTokenExpiration.projector(state);
        expect(selected).toBeNull();
      });
    });

    it('should select null when expiresAt (time) is undefined', () => {
      state = createTokenState({
        accessToken: createAccessToken({
          expiresAt: undefined
        })
      });
      const selected = TokenSelectors.selectAccessTokenExpiration.projector(state);
      expect(selected).toBeNull();
    });
  });

  describe('selectRefreshToken', () => {
    it('should select refresh token', () => {
      const selected = TokenSelectors.selectRefreshToken.projector(state);
      expect(selected).toEqual(state.refreshToken);
    });
  });

  describe('isInitializing', () => {
    it(`should be true if token status is ${TokenStatus.INITIALIZING}`, () => {
      state = createTokenState({
        status: TokenStatus.INITIALIZING
      })
      const selected = TokenSelectors.isInitializing.projector(state);
      expect(selected).toBe(true);
    });

    enumValuesOf(TokenStatus, TokenStatus.INITIALIZING).forEach((notInitializing) => {
      it(`should be false when token status is ${notInitializing}`, () => {
        state = createTokenState({
          status: notInitializing as any
        })
        const selected = TokenSelectors.isInitializing.projector(state);
        expect(selected).toBe(false);
      });
    });
  });

  describe('isLoggedIn', () => {
    it(`should be true if token status is ${TokenStatus.LOGGED_IN}`, () => {
      state = createTokenState({
        status: TokenStatus.LOGGED_IN
      })
      const selected = TokenSelectors.isLoggedIn.projector(state);
      expect(selected).toBe(true);
    });

    enumValuesOf(TokenStatus, TokenStatus.LOGGED_IN).forEach((notLoggedIn) => {
      it(`should be false when token status is ${notLoggedIn}`, () => {
        state = createTokenState({
          status: notLoggedIn as any
        })
        const selected = TokenSelectors.isLoggedIn.projector(state);
        expect(selected).toBe(false);
      });
    });
  });

  describe('isLoggedOut', () => {
    it(`should be true if token status is ${TokenStatus.LOGGED_OUT}`, () => {
      state = createTokenState({
        status: TokenStatus.LOGGED_OUT
      })
      const selected = TokenSelectors.isLoggedOut.projector(state);
      expect(selected).toBe(true);
    });

    enumValuesOf(TokenStatus, TokenStatus.LOGGED_OUT).forEach((notLoggedIn) => {
      it(`should be false when token status is ${notLoggedIn}`, () => {
        state = createTokenState({
          status: notLoggedIn as any
        })
        const selected = TokenSelectors.isLoggedOut.projector(state);
        expect(selected).toBe(false);
      });
    });
  });
});
