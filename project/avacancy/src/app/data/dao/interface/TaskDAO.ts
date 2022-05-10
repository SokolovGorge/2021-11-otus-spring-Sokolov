import {Task} from '../../../model/Task';
import {Category} from '../../../model/Category';
import {CommonDAO} from './CommonDAO';
import {Priority} from '../../../model/Priority';
import {Observable} from 'rxjs';
import {TaskSearchValues} from "../search/SearchObjects";

// специфичные методы для работы с задачами (которые не входят в обычный CRUD)
export interface TaskDAO extends CommonDAO<Task> {

    // поиск задач по параметрам
    // если какой-либо параметр равен null - он не будет учитываться при поиске
    findTasks(taskSearchValues: TaskSearchValues): Observable<any>;

}
