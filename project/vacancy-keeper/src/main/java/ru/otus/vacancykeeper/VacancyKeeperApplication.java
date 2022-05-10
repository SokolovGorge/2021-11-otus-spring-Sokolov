package ru.otus.vacancykeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.vacancykeeper.service.TaskRefresherService;

@SpringBootApplication
@EnableEurekaClient
public class VacancyKeeperApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VacancyKeeperApplication.class, args);

		TaskRefresherService refresherService = context.getBean(TaskRefresherService.class);
		refresherService.refreshAllTasks();
	}

}
