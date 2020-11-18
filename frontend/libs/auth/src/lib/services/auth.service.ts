import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JwtResponse } from '../model/jwt-response.model';
import { AuthSuccess } from '../model/auth-success.model';
import { AUTH_CONFIG_TOKEN, AuthModuleConfig } from '../auth.config';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(@Inject(AUTH_CONFIG_TOKEN) private config: AuthModuleConfig,
              private http: HttpClient) {
  }

  login(username: string, password: string): Observable<AuthSuccess> {
    const request = { username, password };
    return this.http.post<JwtResponse>(this.config.urls.acquireToken, request).pipe(
      map((response) => {
        const parsedAccessToken = JSON.parse(atob(response.access_token.split('.')[1]));
        return {
          tokenType: response.token_type,

          accessToken: response.access_token,
          refreshToken: response.refresh_token,
          parsedAccessToken,

          expiresIn: response.expires_in,
          username: response.username
        };
      })
    );
  }

  refreshToken(refreshToken: string): Observable<AuthSuccess> {
    const request = {
      grant_type: 'refresh_token',
      refresh_token: refreshToken
    };
    return this.http.post<JwtResponse>(this.config.urls.refreshToken, request).pipe(
      map((response) => {
        const parsedAccessToken = JSON.parse(atob(response.access_token.split('.')[1]));
        console.log()
        return {
          tokenType: response.token_type,

          accessToken: response.access_token,
          refreshToken: response.refresh_token,
          parsedAccessToken,

          expiresIn: response.expires_in,
          username: response.username
        };
      })
    );
  }
}
