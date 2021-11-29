package ru.otus.teststudents.dao;

import ru.otus.teststudents.domain.Question;

public interface PublisherDao {

    void publishStart();

    void publishQuestion(Question question);

    void publishResult(boolean result);

}
