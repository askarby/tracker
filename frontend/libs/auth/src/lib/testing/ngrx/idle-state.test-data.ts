import { IdleState } from '../../+state/idle/idle.state';

import merge from "ts-deepmerge";
import { Milliseconds } from '@tracker/shared-utils';

export function createIdleState(override: Partial<IdleState> = {}): IdleState {
  const defaultState: IdleState = {
    customTimeoutMilliseconds: Milliseconds.fromMinutes(5),
  }
  return merge(defaultState, override);
}
