package ru.otus.vacancykeeper.service;

import ru.otus.vacancycommon.dto.VacancyCommon;
import ru.otus.vacancykeeper.dto.TaskDto;

public interface VacancySaver {

    boolean checkAndSaveVacancy(String scode, TaskDto taskDto, VacancyCommon vacancyCommon);
}
