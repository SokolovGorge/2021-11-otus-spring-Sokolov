package ru.otus.teststudents.service;

import ru.otus.teststudents.domain.Answer;
import ru.otus.teststudents.domain.Question;

public interface ReaderAnswerService {

    Answer request(Question question);
}
