import {NativeDateAdapter} from '@angular/material/core';
import {Injectable} from '@angular/core';
import {Platform} from '@angular/cdk/platform';

// @ts-ignore
@Injectable()
export class RuDateAdapter extends NativeDateAdapter {


  constructor(platform: Platform) {
    super("ru-RU", platform);
  }

  getFirstDayOfWeek(): number {
    return 1;
  }

}
