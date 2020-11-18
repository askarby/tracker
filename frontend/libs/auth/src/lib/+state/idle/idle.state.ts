import { Milliseconds, Seconds } from '@tracker/shared-utils';

export interface IdleState {
  /**
   * The timeout (after having idled for a period of time) in milliseconds.
   */
  timeout: number;
}

export function createInitialIdleState(): IdleState {
  return {
    timeout: Milliseconds.fromMinutes(2),
  };
}
