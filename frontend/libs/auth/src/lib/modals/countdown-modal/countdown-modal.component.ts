import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { interval, Observable, Subject, timer } from 'rxjs';
import { map, take, takeUntil } from 'rxjs/operators';
import { Milliseconds, Seconds } from '@tracker/shared-utils';
import { AuthActions } from '../../+state/auth.actions';
import { AUTH_CONFIG_TOKEN, AuthModuleConfig } from '../../auth.config';

export enum CountdownModalResponse {
  Continue = '[CountdownModalResponse] Continue',
  Logout = '[CountdownModalResponse] Logout'
}

@Component({
  selector: 'tracker-countdown-modal',
  templateUrl: './countdown-modal.component.html',
  styleUrls: ['./countdown-modal.component.scss']
})
export class CountdownModalComponent implements OnInit, OnDestroy {

  timeRemainingInSeconds$!: Observable<number>;

  private readonly countdownFromSeconds: number;
  private readonly onDestroy = new Subject();

  constructor(private activeModal: NgbActiveModal,
              @Inject(AUTH_CONFIG_TOKEN) private config: AuthModuleConfig) {
    this.countdownFromSeconds = Seconds.fromMilliseconds(config.idleCountdownMilliseconds);
  }

  ngOnInit(): void {
    const timeElapsed$ = timer(Milliseconds.fromSeconds(this.countdownFromSeconds)).pipe(take(1));

    this.timeRemainingInSeconds$ = interval(Milliseconds.fromSeconds(1)).pipe(
      map((value) => this.countdownFromSeconds - value),
      takeUntil(timeElapsed$)
    );

    this.timeRemainingInSeconds$.pipe(
      takeUntil(this.onDestroy.asObservable())
    ).subscribe({
      complete: () => this.activeModal.close(AuthActions.logout())
    });
  }

  ngOnDestroy(): void {
    this.onDestroy.next();
    this.onDestroy.complete();
  }

  continue(): void {
    this.activeModal.close(CountdownModalResponse.Continue);
  }

  logout(): void {
    this.activeModal.close(CountdownModalResponse.Logout);
  }

}
