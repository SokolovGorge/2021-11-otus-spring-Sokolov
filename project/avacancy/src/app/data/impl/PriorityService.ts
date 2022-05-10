import {Inject, Injectable, InjectionToken} from '@angular/core';
import {PriorityDAO} from "../dao/interface/PriorityDAO";
import {HttpClient} from "@angular/common/http";
import {Priority} from "../../model/Priority";
import {Observable} from "rxjs";
import {PrioritySearchValues} from "../dao/search/SearchObjects";
import {CommonService} from "./CommonService";
import {AppConfigService} from "../../service/app-config.service";

// глобальная переменная для хранения URL
//export const PRIORITY_URL_TOKEN = new InjectionToken<string>('url');


@Injectable({
  providedIn: 'root'
})
export class PriorityService extends CommonService<Priority> implements PriorityDAO {

  constructor(private appConfigService: AppConfigService,
      private http: HttpClient) {
    super(appConfigService.apiBaseUrl + '/priority', http);
  }

  public findPriorities(prioritySearchValues: PrioritySearchValues): Observable<any> {
    return this.http.post<Priority[]>(this.getUrl() + '/search', prioritySearchValues);
  }

}
