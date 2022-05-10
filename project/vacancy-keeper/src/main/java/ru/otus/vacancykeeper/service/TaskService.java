package ru.otus.vacancykeeper.service;

import ru.otus.vacancykeeper.dto.TaskDto;

import java.util.List;

public interface TaskService {

    List<TaskDto> findAllTasks();
}
