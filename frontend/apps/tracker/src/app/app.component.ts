import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { AuthActions } from '@tracker/auth';

@Component({
  selector: 'tracker-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(private store: Store<any>) {
  }

  ngOnInit(): void {
    this.store.dispatch(AuthActions.initialize());

  }
}
