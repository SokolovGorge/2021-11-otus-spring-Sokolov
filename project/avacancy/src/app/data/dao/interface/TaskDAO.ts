import {Task} from '../../../model/Task';
import {CommonDAO} from './CommonDAO';
import {Observable} from "rxjs";
import {TaskSearchValues} from "../search/SearchObjects";

// специфичные методы для работы приоритетами (которые не входят в обычный CRUD)
export interface TaskDAO extends CommonDAO<Task> {

    findTasks(taskSearchValues: TaskSearchValues): Observable<any>;

}
