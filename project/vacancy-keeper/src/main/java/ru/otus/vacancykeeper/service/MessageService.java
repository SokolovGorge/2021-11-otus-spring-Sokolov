package ru.otus.vacancykeeper.service;

import ru.otus.vacancycommon.dto.VacancyCommon;
import ru.otus.vacancykeeper.dto.TaskDto;

public interface MessageService {

    void sendAlarm(TaskDto taskDto, VacancyCommon vacancyCommon);

}
