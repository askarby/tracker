import { TokenState } from './token/token.state';
import { IdleState } from './idle/idle.state';

export interface AuthFeatureState {
  token: TokenState;
  idle: IdleState;
}
