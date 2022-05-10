package ru.otus.hhexplorer.dto.hh;

import lombok.Data;

@Data
public class HHSalary {
    private Long from;
    private Long to;
    private String currency;
    private boolean gross;
}
