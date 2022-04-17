import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Genre} from '../model/Genre';
import {AppConfigService} from './app-config.service';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GenreProviderService {

  private readonly url: string;

  constructor(
    private appConfigService: AppConfigService,
    private http: HttpClient
  ) {
    this.url = appConfigService.apiBaseUrl + '/api/genres/';
  }

  getAll(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.url);
  }

}
