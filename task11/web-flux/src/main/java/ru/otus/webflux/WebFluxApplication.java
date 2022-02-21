package ru.otus.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.webflux.dbutil.DatabaseInitializer;

@SpringBootApplication
public class WebFluxApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(WebFluxApplication.class, args);
		DatabaseInitializer initializer = context.getBean(DatabaseInitializer.class);
		initializer.initData();
	}

}
