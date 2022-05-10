import {Priority} from '../../../model/Priority';
import {CommonDAO} from './CommonDAO';
import {Observable} from "rxjs";
import {PrioritySearchValues} from "../search/SearchObjects";

// специфичные методы для работы приоритетами (которые не входят в обычный CRUD)
export interface PriorityDAO extends CommonDAO<Priority> {

    findPriorities(prioritySearchValues: PrioritySearchValues): Observable<any>;

}
