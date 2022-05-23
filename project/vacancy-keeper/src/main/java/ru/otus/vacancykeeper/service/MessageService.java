package ru.otus.vacancykeeper.service;

import ru.otus.vacancykeeper.dto.TaskDto;
import ru.otus.vacancykeeper.dto.VacancyDto;

public interface MessageService {

    void sendAlarm(TaskDto taskDto, VacancyDto vacancyDto);

}
