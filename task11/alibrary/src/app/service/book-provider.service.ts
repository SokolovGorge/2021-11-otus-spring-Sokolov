import { Injectable } from '@angular/core';
import {Book} from '../model/Book';
import {Observable} from 'rxjs';
import {AppConfigService} from './app-config.service';
import {HttpClient, HttpParams} from '@angular/common/http';
import * as url from 'url';

@Injectable({
  providedIn: 'root'
})
export class BookProviderService {

  private readonly url: string;

  constructor(
    private appConfigService: AppConfigService,
    private http: HttpClient
  ) {
    this.url = appConfigService.apiBaseUrl + '/api/books/';
  }

  getAll(): Observable<Book[]> {
    return this.http.get<Book[]>(this.url);
  }

  addBook(book: Book): Observable<Book> {
    const addUrl: string = this.url + '?title=' + book.title + '&authorId=' + String(book.author.id) + '&genreId=' + String(book.genre.id);
    return this.http.post<Book>(addUrl, {responseType: 'json'});
  }

  update(book: Book): Observable<Book> {
    const updateUrl: string = this.url + '?id=' + String(book.id) + '&title=' + book.title + '&authorId=' + String(book.author.id) + '&genreId=' + String(book.genre.id);
    return this.http.put<Book>(updateUrl, {responseType: 'json'});
  }

  delete(book: Book): Observable<Book> {
    const deleteUrl: string = this.url + book.id;
    return this.http.delete<Book>(deleteUrl);
  }

 }
