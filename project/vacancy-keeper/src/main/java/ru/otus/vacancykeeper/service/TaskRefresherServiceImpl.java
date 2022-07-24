package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.vacancycommon.dto.VacancyCommon;
import ru.otus.vacancykeeper.config.ExplorerConfig;
import ru.otus.vacancykeeper.dto.TaskDto;
import ru.otus.vacancykeeper.explorer.VacancyExplorer;
import ru.otus.vacancykeeper.repository.TaskRepository;

import java.util.List;

@Service
@EnableAsync
@RequiredArgsConstructor
public class TaskRefresherServiceImpl implements TaskRefresherService {

    private  final RestTemplate restTemplate;
    private  final TaskRepository taskRepository;
    private final ExplorerConfig explorerConfig;
    private final VacancySaver vacancySaver;
    private final MessageService messageService;

    @Override
    public void refreshAllTasks() {
        taskRepository.findAll().forEach(task -> updateByTask(new TaskDto(task)));
    }

    @Async
    @Override
    public void refreshByTask(TaskDto taskDto) {
        updateByTask(taskDto);
    }

    private void updateByTask(TaskDto taskDto) {
        explorerConfig.getExplorerMap().forEach((key, value) -> {
            VacancyExplorer explorer = new VacancyExplorer(restTemplate, key, value, taskDto);
            for (List<VacancyCommon> vacancies : explorer) {
                vacancies.forEach(vacancyDto -> {
                    if (vacancySaver.checkAndSaveVacancy(key, taskDto, vacancyDto)) {
                        messageService.sendAlarm(taskDto, vacancyDto);
                    }
                });
            }
        });
    }

}
