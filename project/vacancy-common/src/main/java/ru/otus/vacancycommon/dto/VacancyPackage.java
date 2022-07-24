package ru.otus.vacancycommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VacancyPackage {

    private List<VacancyCommon> items;
    private int pages;
    private  int page;

}
