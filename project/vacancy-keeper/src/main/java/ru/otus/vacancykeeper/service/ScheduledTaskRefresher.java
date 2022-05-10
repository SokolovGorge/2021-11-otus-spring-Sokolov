package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.otus.vacancykeeper.config.ExplorerConfig;
import ru.otus.vacancykeeper.dto.TaskDto;
import ru.otus.vacancykeeper.dto.VacancyDto;
import ru.otus.vacancykeeper.explorer.VacancyExplorer;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTaskRefresher implements TaskRefresherService {

    private  final RestTemplate restTemplate;
    private final TaskService taskService;
    private final ExplorerConfig explorerConfig;
    private final VacancySaver vacancySaver;
    private final MessageService messageService;

    @Scheduled(cron = "0 0 0/6 * * ?")
    @Override
    public void refreshAllTasks() {
        taskService.findAllTasks().forEach(taskDto -> {
            refreshByTask(taskDto);
         });
    }

    @Override
    public void refreshByTask(TaskDto taskDto) {
        explorerConfig.getExplorerMap().entrySet().forEach(entry -> {
            VacancyExplorer explorer = new VacancyExplorer(restTemplate, entry.getKey(), entry.getValue(), taskDto);
            for (List<VacancyDto> vacancies : explorer) {
                vacancies.forEach(vacancyDto -> {
                    if (vacancySaver.checkAndSaveVacancy(entry.getKey(), taskDto, vacancyDto)) {
                        messageService.sendAlarm(taskDto, vacancyDto);
                    }
                });
            }
        });
    }
}
