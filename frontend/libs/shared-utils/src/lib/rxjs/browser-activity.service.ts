import { Injectable } from '@angular/core';
import { fromEvent, merge, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BrowserActivityService {

  click$: Observable<Event>;
  wheel$: Observable<Event>;
  scroll$: Observable<Event>;
  mousemove$: Observable<Event>;
  keyup$: Observable<Event>;
  resize$: Observable<Event>;

  constructor() {
    this.click$ = fromEvent(document, 'click');
    this.wheel$ = fromEvent(document, 'wheel');
    this.scroll$ = fromEvent(document, 'scroll');
    this.mousemove$ = fromEvent(document, 'mousemove');
    this.keyup$ = fromEvent(document, 'keyup');
    this.resize$ = fromEvent(window, 'resize');
  }

  anyActivity$(): Observable<Event> {
    return merge(
      this.click$,
      this.wheel$,
      this.scroll$,
      this.mousemove$,
      this.keyup$,
      this.resize$,
      fromEvent(window, 'scroll'),
      fromEvent(window, 'mousemove')
    );
  }

  anyActivityTimestamp$(): Observable<number> {
    return this.anyActivity$().pipe(map(() => Date.now()));
  }

}
