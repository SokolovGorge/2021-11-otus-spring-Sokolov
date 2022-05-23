import {Professional} from "./Professional";
import {Area} from "./Area";
import {User} from "./user";

export class Task {
    id: number;
    title: string;
    user? :User;
    professional?: Professional;
    area?: Area;
    keywords?: string;

    constructor(id: number, title: string, user?: User, professional?: Professional, area?: Area, keywords?: string) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.professional = professional;
        this.area = area;
        this.keywords = keywords;
    }
}
