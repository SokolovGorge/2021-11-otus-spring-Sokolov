import {Vacancy} from '../../../model/Vacancy';
import {CommonDAO} from './CommonDAO';
import {Observable} from 'rxjs';
import {VacancySearchValues} from "../search/SearchObjects";

// специфичные методы для работы с задачами (которые не входят в обычный CRUD)
export interface VacancyDAO extends CommonDAO<Vacancy> {

    // поиск задач по параметрам
    // если какой-либо параметр равен null - он не будет учитываться при поиске
    findTasks(vacancySearchValues: VacancySearchValues): Observable<any>;

}
