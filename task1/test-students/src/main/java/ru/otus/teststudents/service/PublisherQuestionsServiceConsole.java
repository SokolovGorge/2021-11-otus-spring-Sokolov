package ru.otus.teststudents.service;

import ru.otus.teststudents.domain.Question;

import java.util.List;

public class PublisherQuestionsServiceConsole implements PublisherQuestionsService {

    @Override
    public void publishQuestions(List<Question> quesions) {
        System.out.println("----------------- QUESTIONS -------------------------");
        quesions.forEach(q -> {
            publishQuestion(q);
            System.out.println("");
        });
    }

    private void publishQuestion(Question question) {
        System.out.println("Question: " + question.getQuestion());
        switch (question.getAnswerType()) {
            case FREE:
                System.out.println("Free answer");
                break;
            case CHOICE:
                System.out.println("Answer choice:");
                question.getAnswers().forEach(a -> System.out.println("\t" + a));
        }
    }
}
