export class Vacancy {
    id: number;
    scode: string;
    sid: string;
    name: string;
    salaryMin?: number;
    salaryMax?: number;
    currency?: string;
    schedule?: string;
    address?: string;
    addrLat?: number;
    addrLng?: number;
    employerName?: string;
    employerUrl?: string;
    requirement?: string;
    responsibility?: string;
    sourceUrl?: string;


    constructor(id: number, scode: string, sid: string, name: string, salaryMin?: number, salaryMax?: number, currency?: string, schedule?: string, address?: string, addrLat?: number, addrLng?: number, employerName?: string, employerUrl?: string, requirement?: string, responsibility?: string, sourceUrl?: string) {
        this.id = id;
        this.scode = scode;
        this.sid = sid;
        this.name = name;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.currency = currency;
        this.schedule = schedule;
        this.address = address;
        this.addrLat = addrLat;
        this.addrLng = addrLng;
        this.employerName = employerName;
        this.employerUrl = employerUrl;
        this.requirement = requirement;
        this.responsibility = responsibility;
        this.sourceUrl = sourceUrl;
    }
}
