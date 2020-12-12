import { createFeatureSelector } from '@ngrx/store';
import { ShellFeatureState } from './shell-feature.state';

export const ShellSelectors = {
  selectShellState: createFeatureSelector<ShellFeatureState>('shell'),
}
