package ru.otus.vacancykeeper.service;

import ru.otus.vacancykeeper.dto.TaskDto;

public interface TaskRefresherService {

    void refreshAllTasks();

    void refreshByTask(TaskDto taskDto);
}
