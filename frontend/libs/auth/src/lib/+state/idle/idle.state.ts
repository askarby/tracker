export interface IdleState {
  /**
   * Number of milliseconds to idle before being logged out.
   *
   * Will only hold a value when user specified.
   */
  customTimeoutMilliseconds: number | null;
}

export function createInitialIdleState(): IdleState {
  return {
    customTimeoutMilliseconds: null,
  };
}
