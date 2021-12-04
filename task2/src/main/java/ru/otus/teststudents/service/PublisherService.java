package ru.otus.teststudents.service;

import ru.otus.teststudents.domain.Question;

public interface PublisherService {

    void publishStart();

    void publishQuestion(Question question);

    void publishResult(boolean result);

}
