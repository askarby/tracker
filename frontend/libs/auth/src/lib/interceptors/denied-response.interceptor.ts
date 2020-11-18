import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthActions } from '../+state/auth.actions';

@Injectable()
export class DeniedResponseInterceptor implements HttpInterceptor {
  static readonly Codes = [ 401, 403 ];

  constructor(private store: Store<any>) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
      .pipe(tap((event) => {
        if (event instanceof HttpErrorResponse && DeniedResponseInterceptor.Codes.includes(event.status)) {
          this.store.dispatch(AuthActions.logout());
        }
      }));
  }
}
