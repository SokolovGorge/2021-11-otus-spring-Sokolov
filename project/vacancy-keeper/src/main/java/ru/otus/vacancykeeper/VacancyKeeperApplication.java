package ru.otus.vacancykeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class VacancyKeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacancyKeeperApplication.class, args);
	}

}
