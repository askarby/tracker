import { createFeatureSelector } from '@ngrx/store';
import { AuthFeatureState } from './auth-feature.state';

export namespace AuthSelectors {
  export const selectAuthState = createFeatureSelector<AuthFeatureState>('auth');
}
