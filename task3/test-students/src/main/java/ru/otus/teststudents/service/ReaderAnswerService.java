package ru.otus.teststudents.service;

import ru.otus.teststudents.domain.Answer;
import ru.otus.teststudents.domain.Question;

import java.util.Locale;

public interface ReaderAnswerService {

    Answer request(Question question, Locale locale);
}
