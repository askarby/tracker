import merge from 'ts-deepmerge';
import { AUTH_CONFIG_TOKEN, AuthModuleConfig } from '@tracker/auth';
import { Provider } from '@angular/core';
import { Milliseconds } from '@tracker/shared-utils';

export function provideAuthDefaultConfig(): Provider {
  return {
    provide: AUTH_CONFIG_TOKEN,
    useValue: createAuthModuleConfig(),
  }
}

export function createAuthModuleConfig(override: Partial<AuthModuleConfig> = {}): AuthModuleConfig {
  const defaultAuthModuleConfig: AuthModuleConfig = {
    idleTimeoutMilliseconds: Milliseconds.fromMinutes(5),
    idleCountdownMilliseconds: Milliseconds.fromMinutes(1),
    urls: {
      acquireToken: 'http://127.0.0.1/acquire-token',
      refreshToken: 'http://127.0.0.1/refresh-token',
    }
  };
  return merge(defaultAuthModuleConfig, override);
}
