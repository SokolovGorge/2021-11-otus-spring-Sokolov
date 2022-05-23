import {Injectable} from "@angular/core";
import {CommonService} from "./CommonService";
import {Professional} from "../../model/Professional";
import {ProfessionalDAO} from "../dao/interface/professional-dao";
import {AppConfigService} from "../../service/app-config.service";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class ProfessionalService extends CommonService<Professional> implements  ProfessionalDAO {

    constructor(private appConfigService: AppConfigService,
                private http: HttpClient) {
        super(appConfigService.apiBaseUrl + '/professional', http);
    }

}
