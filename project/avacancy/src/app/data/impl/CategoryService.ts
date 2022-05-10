import {Inject, Injectable, InjectionToken} from '@angular/core';
import {CategoryDAO} from "../dao/interface/CategoryDAO";
import {Category} from "../../model/Category";
import {Observable} from "rxjs";
import {CategorySearchValues} from "../dao/search/SearchObjects";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "./CommonService";
import {AppConfigService} from "../../service/app-config.service";

// глобальная переменная для хранения URL
//export const CATEGORY_URL_TOKEN = new InjectionToken<string>('url');


@Injectable({
  providedIn: 'root'
})
export class CategoryService extends CommonService<Category> implements CategoryDAO {


  constructor(private appConfigService: AppConfigService,
      private http: HttpClient
  ) {
    super(appConfigService.apiBaseUrl + '/category', http)
  }

  public findCategories(categorySearchValues: CategorySearchValues): Observable<any> {
    return this.http.post<Category[]>(this.getUrl() + '/search', categorySearchValues);
  }

}
