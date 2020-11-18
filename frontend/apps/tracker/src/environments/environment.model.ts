export interface Environment {
  /**
   * Indicates if running in production (or not).
   */
  production: boolean;

  /**
   * Base url of API endpoints.
   */
  apiBaseUrl: string;

  /**
   * Sub-environment, related to security concerns.
   */
  security: {
    /**
     * Number of milliseconds (without any activity) before being automatically logged out.
     */
    idleTimeoutMilliseconds: number;

    /**
     * Number of milliseconds to inform user that he will be automatically logged out (prior to the action).
     */
    idleCountdownMilliseconds: number;
  }
}
