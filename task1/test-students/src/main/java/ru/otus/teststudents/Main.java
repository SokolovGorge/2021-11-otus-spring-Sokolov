package ru.otus.teststudents;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.teststudents.service.StudentsService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        StudentsService studentsService = context.getBean(StudentsService.class);
        studentsService.prepareQuestions();
        studentsService.publishQuestions();
        context.close();
    }

}
