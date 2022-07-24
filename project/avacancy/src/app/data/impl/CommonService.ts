import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

export class CommonService<T> {

  private readonly url: string;

  constructor(url: string,
              private httpClient: HttpClient) {
    this.url = url;
  }

  public getUrl(): string {
    return this.url;
  }

  // получить все значения
  public getAll(): Observable<T[]> {
    return this.httpClient.get<T[]>(this.url + '/all');
  }

  // получить одно значение по id
  public get(id: number): Observable<T> {
    return this.httpClient.get<T>(this.url + '/' + id);
  }

  // обновить значение
  public update(obj: T): Observable<T> {
    return this.httpClient.put<T>(this.url, obj);
  }

  // удалить значение
  public delete(id: number): Observable<T> {
    return this.httpClient.delete<T>(this.url + '/' + id);
  }

  // добавить значение
  public add(obj: T): Observable<T> {
    return this.httpClient.post<T>(this.url, obj);
  }

}
