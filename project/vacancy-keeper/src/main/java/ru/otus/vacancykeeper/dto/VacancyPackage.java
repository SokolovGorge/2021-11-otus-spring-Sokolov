package ru.otus.vacancykeeper.dto;

import lombok.Data;

import java.util.List;

@Data
public class VacancyPackage {

    private List<VacancyDto> items;
    private Integer pages;
    private  Integer page;

}
