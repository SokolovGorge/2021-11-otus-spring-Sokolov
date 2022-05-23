import {Injectable} from "@angular/core";
import {CommonService} from "./CommonService";
import {Area} from "../../model/Area";
import {AreaDAO} from "../dao/interface/area-dao";
import {AppConfigService} from "../../service/app-config.service";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class AreaService extends CommonService<Area> implements AreaDAO {

    constructor(private appConfigService: AppConfigService,
                private http: HttpClient) {
        super(appConfigService.apiBaseUrl + '/area', http);
    }

}
