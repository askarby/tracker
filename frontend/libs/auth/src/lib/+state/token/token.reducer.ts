import { createMutableReducer, mutableOn } from 'ngrx-etc';
import { createInitialTokenState, TokenState, TokenStatus } from './token.state';
import { TokenActions } from './token.actions';
import { AuthSuccess } from '../../model/auth-success.model';

export const tokenReducer = createMutableReducer<TokenState>(
  createInitialTokenState(),
  mutableOn(TokenActions.loginSuccess, (draft, { data }) => {
    draft.status = TokenStatus.LOGGED_IN;
    updateToken(draft, data);
  }),
  mutableOn(TokenActions.loginFailed, (draft, { error }) => {
    clearToken(draft, error);
  }),
  mutableOn(TokenActions.logoutSuccess, (draft) => {
    clearToken(draft);
  }),
  mutableOn(TokenActions.refreshSuccess, (draft, { data} ) => {
    updateToken(draft, data);
  }),
  mutableOn(TokenActions.refreshFailed, (draft, { error }) => {
    clearToken(draft, error);
  }),
);

function updateToken(draft: TokenState, data: AuthSuccess) {
  draft.accessToken = {
    raw: data.accessToken,
    username: data.username,
    expiresAt: data.parsedAccessToken.exp,
    roles: data.parsedAccessToken.roles
  };
  draft.refreshToken = data.refreshToken;
  draft.error = null;
}

function clearToken(draft: TokenState, error?: any) {
  draft.status = TokenStatus.LOGGED_OUT;
  draft.accessToken = null;
  draft.refreshToken = null;
  draft.error = error;
}
