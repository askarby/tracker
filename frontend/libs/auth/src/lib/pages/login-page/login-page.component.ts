import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { TokenActions } from '../../+state/token/token.actions';
import { HttpClient } from '@angular/common/http';
import { AuthActions } from '../../+state/auth.actions';

@Component({
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent {
  form: FormGroup;

  constructor(private store: Store<any>,
              private fb: FormBuilder,
              private http: HttpClient) {
    this.form = fb.group({
      username: fb.control('', {
        validators: [Validators.required]
      }),
      password: fb.control('', {
        validators: [Validators.required]
      })
    });
  }

  login() {
    if (this.form.valid && this.form.dirty) {
      const username = this.form.controls['username'].value;
      const password = this.form.controls['password'].value;
      this.store.dispatch(AuthActions.login({ username, password }));
    }
  }

  // TODO: Remove this, only added for debugging (manual testing)
  refresh() {
    this.store.dispatch(TokenActions.refresh());
  }

  sayHello(name: string) {
    this.http.get(`http://localhost:8080/greeting/hello/${name}`).subscribe((response) => {
      console.log(`> sayHello("${name}") responded with: `, response);
    });
  }

  saySecuredHello() {
    this.http.get(`http://localhost:8080/greeting/secured-hello`).subscribe((response) => {
      console.log(`> saySecuredHello() responded with: `, response);
    });
  }

  logout() {
    this.store.dispatch(AuthActions.logout());
  }
}
