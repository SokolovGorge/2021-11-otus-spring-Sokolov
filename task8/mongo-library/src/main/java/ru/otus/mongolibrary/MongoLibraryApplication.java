package ru.otus.mongolibrary;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableMongock
@SpringBootApplication
@EnableConfigurationProperties
public class MongoLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoLibraryApplication.class, args);
	}

}
