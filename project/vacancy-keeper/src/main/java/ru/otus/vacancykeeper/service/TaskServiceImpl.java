package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vacancykeeper.dto.TaskDto;
import ru.otus.vacancykeeper.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    @Override
    public List<TaskDto> findAllTasks() {
        return taskRepository.findAll().stream().map(TaskDto::new).collect(Collectors.toList());
    }

}
