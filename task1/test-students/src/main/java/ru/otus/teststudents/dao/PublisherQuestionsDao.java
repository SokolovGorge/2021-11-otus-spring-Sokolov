package ru.otus.teststudents.dao;

import ru.otus.teststudents.domain.Question;

import java.util.List;

public interface PublisherQuestionsDao {

    void publishQuestions(List<Question> quesions);
}
