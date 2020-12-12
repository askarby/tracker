import { Injectable, Type } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { BsModalService } from 'ngx-bootstrap/modal';
import { ModalController } from './modal.controller';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  constructor(private modalService: BsModalService) { }

  displayModal<T>(component: Type<any>): Observable<T> {
    const subject = new Subject<T>();
    const controller: ModalController<T> = {
      close(response: T | undefined) {
        subject.next(response);
        subject.complete();
      }
    };

    this.modalService.show(component, {
      providers: [
        {
          provide: ModalController,
          useValue: controller,
        }
      ]
    })
    return subject.asObservable();
  }
}
