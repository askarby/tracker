import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'tracker-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginFormComponent {
  @Input()
  form!: FormGroup;

  @Output()
  login = new EventEmitter();

  showErrors = false;

  get username(): FormControl {
    return this.getControl('username');
  }

  get password() {
    return this.getControl('password');
  }

  private getControl(name: string): FormControl {
    const control = this.form.get(name)
    if (!control) {
      throw new Error(`Unable to acquire "${name}" FormControl from form`);
    }
    return control as FormControl;
  }

  attemptLogin() {
    if (this.form.valid) {
      this.showErrors = false;
      this.login.emit();
    } else {
      this.showErrors = true;
    }
  }
}
