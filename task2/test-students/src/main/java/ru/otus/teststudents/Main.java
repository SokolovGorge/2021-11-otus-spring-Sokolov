package ru.otus.teststudents;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.teststudents.service.ExaminerService;

@PropertySource("application.properties")
@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        ExaminerService examinerService = context.getBean(ExaminerService.class);
        examinerService.exam();
    }

}
