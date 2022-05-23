package ru.otus.hhexplorer.dto.common;

import lombok.Data;

@Data
public class Vacancy {

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
