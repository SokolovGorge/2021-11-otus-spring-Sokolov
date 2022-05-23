import {Inject, Injectable, InjectionToken} from '@angular/core';
import {VacancyDAO} from "../dao/interface/VacancyDAO";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { Vacancy } from 'src/app/model/Vacancy';
import {VacancySearchValues} from "../dao/search/SearchObjects";
import {CommonService} from "./CommonService";
import {AppConfigService} from "../../service/app-config.service";

// глобальная переменная для хранения URL
//export const TASK_URL_TOKEN = new InjectionToken<string>('url');


@Injectable({
  providedIn: 'root'
})
export class VacancyService extends CommonService<Vacancy> implements VacancyDAO {

  constructor(private appConfigService: AppConfigService,
      private http: HttpClient) {
    super(appConfigService.apiBaseUrl + '/vacancy', http);
  }

  public findTasks(vacancySearchValues: VacancySearchValues): Observable<any> {
    return this.http.post<Vacancy[]>(this.getUrl() + '/search', vacancySearchValues);
  }

}
