export class Seconds {
  static fromMinutes(minutes: number): number {
    return minutes * 60;
  }

  static fromMilliseconds(milliseconds: number) {
    return milliseconds / 1000;
  }
}

export class Milliseconds {
  static fromMinutes(minutes: number): number {
    return Milliseconds.fromSeconds(Seconds.fromMinutes(minutes));
  }

  static fromSeconds(seconds: number) {
    return seconds * 1000;
  }
}
