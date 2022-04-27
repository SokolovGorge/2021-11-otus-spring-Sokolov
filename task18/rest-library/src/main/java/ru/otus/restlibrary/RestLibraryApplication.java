package ru.otus.restlibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class RestLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestLibraryApplication.class, args);
	}

}
