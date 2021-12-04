package ru.otus.teststudents.service;

import ru.otus.teststudents.domain.Question;

import java.util.List;

public interface PublisherQuestionsService {

    void publishQuestions(List<Question> quesions);
}
