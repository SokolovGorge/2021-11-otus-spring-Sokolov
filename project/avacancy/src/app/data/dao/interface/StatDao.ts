import {Stat} from "../../../model/Stat";
import {Observable} from "rxjs";
import {TaskSearchValues} from "../search/SearchObjects";

export interface StatDao {

    getStat(taskSearchValues: TaskSearchValues): Observable<Stat>;
}
