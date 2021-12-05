package ru.otus.teststudents;

import org.springframework.context.annotation.*;
import ru.otus.teststudents.exceptions.QuestionException;
import ru.otus.teststudents.service.*;

@PropertySource("application.properties")
@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        StudentsService studentsService = context.getBean(StudentsService.class);
        try {
            studentsService.examStudent();
        } catch (QuestionException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public WriterService writerService() {
        return new WriterServiceImpl(System.out);
    }

    @Bean
    public ReaderService readerService() {
        return new ReaderServiceImpl(System.in);
    }
}
