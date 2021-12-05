package ru.otus.teststudents.service;

import ru.otus.teststudents.domain.Question;

public interface PrinterQuestionService {

    void printExamStart();

    void printQuestion(Question question);

    void printResult(boolean result);

}
