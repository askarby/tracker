import { Inject, Injectable } from '@angular/core';
import { WINDOW_TOKEN } from '../tokens/window.token';
import { Observable, of, throwError } from 'rxjs';
import { switchMap } from 'rxjs/operators';

/**
 * Service for working with LocalStorage.
 *
 * Will handle:
 * - Conversion to Observables
 * - Stringification / Parsing to and from JSON
 */
@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor(@Inject(WINDOW_TOKEN) private window: Window) {
  }

  /**
   * Retrieves an item from LocalStorage.
   * @param key the key of the item to retrieve
   */
  public get<T>(key: string): Observable<T | null> {
    const raw = this.window.localStorage.getItem(key);
    let asJson = null;
    if (raw !== null) {
      asJson = JSON.parse(raw);
    }
    return of(asJson);
  }

  /**
   * Persists an item to LocalStorage
   * @param key the key to store the item with
   * @param value the value to store (as JSON) in LocalStorage
   */
  public set<T>(key: string, value: T): Observable<T> {
    try {
      const asJson = JSON.stringify(value);
      this.window.localStorage.setItem(key, asJson);
      return of(value);
    } catch (e) {
      return throwError(e);
    }
  }

  /**
   * Removes an item from LocalStorage
   * @param key the key the item (to remove) is stored with
   * @return the item stored with the specific key, or null if none (as an Observable)
   */
  remove<T>(key: string): Observable<T | null> {
    return this.get<T>(key).pipe(
      switchMap((found) => {
        try {
          if (found) {
            this.window.localStorage.removeItem(key);
          }
          return of(found);
        } catch (e) {
          return throwError(e);
        }
      })
    );
  }
}
