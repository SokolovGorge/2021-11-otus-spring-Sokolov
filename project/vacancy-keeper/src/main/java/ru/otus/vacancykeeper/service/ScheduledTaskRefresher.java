package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTaskRefresher  {

    private final TaskRefresherService taskRefresherService;

    @Scheduled(cron = "${name-of-the-cron:0 0 0/6 * * ?}")
    public void refreshAllTasks() {
        taskRefresherService.refreshAllTasks();
    }

}
