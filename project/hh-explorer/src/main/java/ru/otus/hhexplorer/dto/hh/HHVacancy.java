package ru.otus.hhexplorer.dto.hh;

import lombok.Data;

import java.util.Date;

@Data
public class HHVacancy {

    private String id;
    private boolean premium;
    private String name;
    private HHArea area;
    private HHSalary salary;
    private HHItemType type;
    private HHAddress address;
    private Date published_at;
    private Date created_at;
    private String url;
    private String alternate_url;
    private HHEmployer employer;
    private HHSnippet snippet;
    private HHItemType schedule;
}
