import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Author} from '../model/Author';
import {AppConfigService} from './app-config.service';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthorProviderService {

  private readonly url: string;

  constructor(
    private appConfigService: AppConfigService,
    private http: HttpClient
  ) {
    this.url = appConfigService.apiBaseUrl + '/api/authors/';
  }

  getAll(): Observable<Author[]> {
    return this.http.get<Author[]>(this.url);
  }

}
