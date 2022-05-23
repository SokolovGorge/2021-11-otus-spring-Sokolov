package ru.otus.vacancykeeper.service;

import ru.otus.vacancykeeper.domain.Task;
import ru.otus.vacancykeeper.dto.TaskDto;

import java.util.List;

public interface TaskService {

    List<TaskDto> findAllTasks();

    List<Task> findTasksOrderByTitle();

    List<Task> findTasksByTitle(String title);

    Task findById(Long id);

    Task save(Task task);

    void deleteById(Long id);
}
