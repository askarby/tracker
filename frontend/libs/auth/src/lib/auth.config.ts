import { InjectionToken } from '@angular/core';

export const AUTH_CONFIG_TOKEN = new InjectionToken<AuthModuleConfig>('Token for AuthModule configuration-object');

export interface AuthModuleConfig {
  /**
   * Number of milliseconds (without any activity) before being automatically logged out.
   */
  idleTimeoutMilliseconds: number;

  /**
   * Number of milliseconds to inform user that he will be automatically logged out (prior to the action).
   */
  idleCountdownMilliseconds: number;

  /**
   * URLs (endpoints) used by AuthModule.
   */
  urls: {
    /**
     * The path to the endpoint used to acquire an access token.
     */
    acquireToken: string;

    /**
     * The path to the endpoint used to acquire a new access token from a refresh token.
     */
    refreshToken: string;
  }
}
