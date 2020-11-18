import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'readableTime'
})
export class ReadableTimePipe implements PipeTransform {
  static readonly SecondsInMinute = 60;
  static readonly SecondsInHour = ReadableTimePipe.SecondsInMinute * 60;
  static readonly SecondsInDay = ReadableTimePipe.SecondsInHour * 24;

  transform(seconds: number): string {
    let remaining = seconds;
    const count = {
      days: 0,
      hours: 0,
      minutes: 0,
      seconds: 0,
    }
    if (remaining >= ReadableTimePipe.SecondsInDay) {
      count.days = Math.floor(remaining / ReadableTimePipe.SecondsInDay);
      remaining -= count.days * ReadableTimePipe.SecondsInDay;
    }
    if (remaining >= ReadableTimePipe.SecondsInHour) {
      count.hours = Math.floor(remaining / ReadableTimePipe.SecondsInHour);
      remaining -= count.hours * ReadableTimePipe.SecondsInHour;
    }
    if (remaining >= ReadableTimePipe.SecondsInMinute) {
      count.minutes = Math.floor(remaining / ReadableTimePipe.SecondsInMinute);
      remaining -= count.minutes * ReadableTimePipe.SecondsInMinute;
    }
    count.seconds = remaining;

    let result = '';
    if (count.seconds > 0) {
      result = `${count.seconds}s`;
    }
    if (count.minutes > 0) {
      result = `${count.minutes}m ${result}`;
    }
    if (count.hours > 0) {
      result = `${count.hours}t ${result}`;
    }
    if (count.days > 0) {
      result = `${count.days}d ${result}`;
    }
    return result;
  }

}
