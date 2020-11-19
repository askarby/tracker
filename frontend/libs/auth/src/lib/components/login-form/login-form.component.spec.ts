import { Spectator, createComponentFactory } from '@ngneat/spectator/jest';

import { LoginFormComponent } from './login-form.component';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

describe('LoginFormComponent', () => {
  const createComponent = createComponentFactory({
    component: LoginFormComponent,
    imports: [
      ReactiveFormsModule
    ]
  });

  let spectator: Spectator<LoginFormComponent>;
  let component: LoginFormComponent;

  beforeEach(() => {
    const fb = new FormBuilder();
    spectator = createComponent({
      props: {
        form: fb.group({
          username: fb.control(''),
          password: fb.control('')
        })
      }
    });
    component = spectator.component;
  });

  it('should be create', () => {
    expect(spectator.component).toBeTruthy();
  });
});
