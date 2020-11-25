import { createFeatureSelector } from '@ngrx/store';
import { AuthFeatureState } from './auth-feature.state';

export const AuthSelectors = {
  selectAuthState: createFeatureSelector<AuthFeatureState>('auth'),
}
