package ru.otus.hhexplorer.dto.common;

import lombok.Data;

import java.util.List;

@Data
public class VacancyPackage {
    private List<Vacancy> items;
    private Integer pages;
    private  Integer page;
}
