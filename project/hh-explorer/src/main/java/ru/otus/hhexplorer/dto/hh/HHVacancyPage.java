package ru.otus.hhexplorer.dto.hh;

import lombok.Data;

@Data
public class HHVacancyPage {

    private HHVacancy[] items;
    private Integer found;
    private Integer pages;
    private Integer per_page;
    private  Integer page;
}
