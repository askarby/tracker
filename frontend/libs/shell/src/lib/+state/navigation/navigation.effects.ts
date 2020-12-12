import { Inject, Injectable } from '@angular/core';
import { Actions, createEffect, ofType, OnInitEffects, ROOT_EFFECTS_INIT } from '@ngrx/effects';
import { map } from 'rxjs/operators';
import { SHELL_CONFIG_TOKEN, ShellModuleConfig } from '../../shell.config';
import { NavigationActions } from './navigation.actions';
import { Action } from '@ngrx/store';

@Injectable({ providedIn: 'root' })
export class NavigationEffects implements OnInitEffects {

  /**
   * This is directly initialized (populating state based on `ShellModuleConfig`-object),
   * which is static  - in the future, this could be populated from a backend (eg. based on JWT token properties)
   */
  initialize$ = createEffect(() =>
    this.actions$.pipe(
      ofType(NavigationActions.loadNavigation),
      map(() => {
        const config = this.config.navigation;
        return NavigationActions.navigationLoaded({
          site: config.site.items,
          admin: config.admin,
          footer: config.footer,
        })
      })
    )
  );

  constructor(private actions$: Actions,
              @Inject(SHELL_CONFIG_TOKEN) private config: ShellModuleConfig) {
  }

  ngrxOnInitEffects(): Action {
    return NavigationActions.loadNavigation();
  }
}
