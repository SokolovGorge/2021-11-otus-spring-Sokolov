package ru.otus.hhexplorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HhExplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HhExplorerApplication.class, args);
	}

}
