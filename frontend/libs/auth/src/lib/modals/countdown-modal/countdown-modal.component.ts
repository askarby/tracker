import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { interval, Observable, Subject, timer } from 'rxjs';
import { map, take, takeUntil } from 'rxjs/operators';
import { Milliseconds, Seconds } from '@tracker/shared-utils';
import { AuthActions } from '../../+state/auth.actions';
import { AUTH_CONFIG_TOKEN, AuthModuleConfig } from '../../auth.config';
import { ModalController } from '@tracker/shell';
import { Action } from '@ngrx/store';

@Component({
  templateUrl: './countdown-modal.component.html',
  styleUrls: ['./countdown-modal.component.scss']
})
export class CountdownModalComponent implements OnInit, OnDestroy {

  timeRemainingInSeconds$!: Observable<number>;

  private readonly countdownFromSeconds: number;
  private readonly onDestroy = new Subject();

  constructor(private controller: ModalController<Action>,
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
      complete: () => this.logout()
    });
  }

  ngOnDestroy(): void {
    this.onDestroy.next();
    this.onDestroy.complete();
  }

  continue(): void {
    this.controller.close();
  }

  logout(): void {
    this.controller.close(AuthActions.logout());
  }

}
