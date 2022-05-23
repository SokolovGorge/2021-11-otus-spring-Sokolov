package ru.otus.vacancykeeper.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancySearchValues {

    private Long taskId;
    private String text;
    private Integer pageSize;
    private Integer pageNumber;

}
