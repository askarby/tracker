import { createPipeFactory, SpectatorPipe } from '@ngneat/spectator/jest';

import { ReadableTimePipe } from './readable-time.pipe';

describe('ReadableTimePipe ', () => {
  const createPipe = createPipeFactory(ReadableTimePipe);
  let spectator: SpectatorPipe<ReadableTimePipe>;

  const oneDay = 60 * 60 * 24;
  const fourHours = 60 * 60 * 4;
  const twoMinutes = 60 * 2;
  const thirtySixSeconds = 36;

  it('should format days, hours, minutes and seconds', () => {
    spectator = createPipe(`<div>{{ seconds | readableTime }}</div>`, {
      hostProps: {
        seconds: oneDay + fourHours + twoMinutes + thirtySixSeconds,
      }
    });

    expect(spectator.element).toHaveText('1d 4t 2m 36s');
  });

  it('should format hours, minutes and seconds', () => {
    spectator = createPipe(`<div>{{ seconds | readableTime }}</div>`, {
      hostProps: {
        seconds: fourHours + twoMinutes + thirtySixSeconds,
      }
    });

    expect(spectator.element).toHaveText('4t 2m 36s');
  });

  it('should format minutes and seconds', () => {
    spectator = createPipe(`<div>{{ seconds | readableTime }}</div>`, {
      hostProps: {
        seconds: twoMinutes + thirtySixSeconds,
      }
    });

    expect(spectator.element).toHaveText('2m 36s');
  });

  it('should format days', () => {
    spectator = createPipe(`<div>{{ seconds | readableTime }}</div>`, {
      hostProps: {
        seconds: oneDay,
      }
    });

    expect(spectator.element).toHaveText('1d');
  });

  it('should format hours', () => {
    spectator = createPipe(`<div>{{ seconds | readableTime }}</div>`, {
      hostProps: {
        seconds: fourHours,
      }
    });

    expect(spectator.element).toHaveText('4t');
  });

  it('should format minutes', () => {
    spectator = createPipe(`<div>{{ seconds | readableTime }}</div>`, {
      hostProps: {
        seconds: twoMinutes,
      }
    });

    expect(spectator.element).toHaveText('2m');
  });

  it('should format seconds', () => {
    spectator = createPipe(`<div>{{ seconds | readableTime }}</div>`, {
      hostProps: {
        seconds: thirtySixSeconds,
      }
    });

    expect(spectator.element).toHaveText('36s');
  });

  [null, undefined].forEach((absent) => {
    it(`should format absent seconds (${absent})`, () => {
      spectator = createPipe(`<div>{{ seconds | readableTime }}</div>`, {
        hostProps: {
          seconds: absent,
        }
      });

      expect(spectator.element.textContent).toEqual('');
    });
  })
});
