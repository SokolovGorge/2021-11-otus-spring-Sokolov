package ru.otus.teststudents.dao;

import org.springframework.stereotype.Component;
import ru.otus.teststudents.domain.Question;

@Component
public class PublisherDaoConsole implements PublisherDao {

    @Override
    public void publishStart() {
        System.out.println("*********************** Start examination *********************************");
    }

    @Override
    public void publishQuestion(Question question) {
        System.out.println("Question: " + question.getQuestion());
        System.out.println("Answer choice");
        for (int i = 0; i < question.getAnswers().size(); i++) {
            System.out.println(String.valueOf(i + 1) + ". " + question.getAnswers().get(i));
        }
    }

    @Override
    public void publishResult(boolean result) {
        System.out.println("*********************** End examination *********************************");
        if (result) {
            System.out.println("You have successfully passed the exam!");
        } else {
            System.out.println("You didn't pass the exam. Try again.");
        }
        System.out.println("*************************************************************************");
    }
}
