import { TranslocoTestingModule, TranslocoConfig } from '@ngneat/transloco';
import en from '../assets/i18n/en.json';
import es from '../assets/i18n/en.json';

export function getTranslocoTestModule(config: Partial<TranslocoConfig> = {}) {
  return TranslocoTestingModule.withLangs(
    { en, es },
    {
      availableLangs: ['da', 'en'],
      defaultLang: 'en',
      ...config
    }
  );
}
