package ru.otus.vacancycommon.dto;

import lombok.Data;

@Data
public class VacancyCommon {

    private String id;
    private String name;
    private Long salaryMin;
    private  Long salaryMax;
    private  String currency;
    private String schedule;
    private String address;
    private float addressLat;
    private float addressLng;
    private String employerName;
    private String employerURL;
    private String requirement;
    private String responsibility;
    private String sourceURL;

}
