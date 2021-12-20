package ru.otus.teststudents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.teststudents.service.ExaminerService;

@SpringBootApplication
public class TestStudentsApplication {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TestStudentsApplication.class, args);

        ExaminerService examinerService = context.getBean(ExaminerService.class);
        examinerService.exam();
    }
}
