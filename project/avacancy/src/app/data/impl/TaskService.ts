import {Inject, Injectable, InjectionToken} from '@angular/core';
import {TaskDAO} from "../dao/interface/TaskDAO";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { Task } from 'src/app/model/Task';
import {TaskSearchValues} from "../dao/search/SearchObjects";
import {CommonService} from "./CommonService";
import {AppConfigService} from "../../service/app-config.service";

// глобальная переменная для хранения URL
//export const TASK_URL_TOKEN = new InjectionToken<string>('url');


@Injectable({
  providedIn: 'root'
})
export class TaskService extends CommonService<Task> implements TaskDAO {

  constructor(private appConfigService: AppConfigService,
      private http: HttpClient) {
    super(appConfigService.apiBaseUrl + '/task', http);
  }

  public findTasks(taskSearchValues: TaskSearchValues): Observable<any> {
    return this.http.post<Task[]>(this.getUrl() + '/search', taskSearchValues);
  }

}
