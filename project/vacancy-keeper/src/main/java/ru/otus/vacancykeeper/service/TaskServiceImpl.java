package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vacancykeeper.domain.Task;
import ru.otus.vacancykeeper.dto.TaskDto;
import ru.otus.vacancykeeper.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserServiceImpl userServiceImpl;
    private final TaskRefresherService taskRefresherService;

    @Transactional(readOnly = true)
    @Override
    public List<TaskDto> findAllTasks() {
        return taskRepository.findAll().stream().map(TaskDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> findTasksOrderByTitle() {
        return taskRepository.findTasksByUserOrderByTitle(userServiceImpl.getCurrentUser());
    }

    @Override
    public List<Task> findTasksByTitle(String title) {
        return taskRepository.findTasksByUserAndTitle(userServiceImpl.getCurrentUser(), title);
    }

    @Transactional(readOnly = true)
    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public Task save(Task task) {
        boolean isNew = task.getId() == null;
        if (null == task.getUser()) {
            task.setUser(userServiceImpl.getCurrentUser());
        }
        Task newTask = taskRepository.save(task);
        if (isNew) {
            taskRefresherService.refreshByTask(new TaskDto(newTask));
        }
        return newTask;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

}
