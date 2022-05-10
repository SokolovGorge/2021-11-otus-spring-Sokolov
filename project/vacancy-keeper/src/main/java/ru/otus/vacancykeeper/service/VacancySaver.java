package ru.otus.vacancykeeper.service;

import ru.otus.vacancykeeper.dto.TaskDto;
import ru.otus.vacancykeeper.dto.VacancyDto;

public interface VacancySaver {

    boolean checkAndSaveVacancy(String scode, TaskDto taskDto, VacancyDto vacancyDto);
}
