import {Inject, Injectable, InjectionToken} from '@angular/core';
import {StatDao} from "../dao/interface/StatDao";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Stat} from "../../model/Stat";
import {TaskSearchValues} from "../dao/search/SearchObjects";
import {AppConfigService} from "../../service/app-config.service";

//export const STAT_URL_TOKEN = new InjectionToken<string>('url');

@Injectable({
  providedIn: 'root'
})
export class StatService implements StatDao {

  private url: string;

  constructor(private appConfigService: AppConfigService,
      private http: HttpClient) {
    this.url = appConfigService.apiBaseUrl + '/taskstat';
  }

  getStat(taskSearchValues: TaskSearchValues): Observable<Stat> {
    return this.http.post<Stat>(this.url + '/statistic', taskSearchValues);
  }
}
