package ru.otus.vacancykeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.vacancykeeper.domain.Task;

@AllArgsConstructor
@Data
public class TaskDto {

    private Long id;
    private String title;
    private String profCode;
    private String areaCode;
    private String keywords;

    public TaskDto(Task task) {
        id = task.getId();
        title = task.getTitle();
        profCode = task.getProfessional() == null ? null : task.getProfessional().getCode();
        areaCode = task.getArea() == null ? null : task.getArea().getCode();
        keywords = task.getKeywords();
    }

}
