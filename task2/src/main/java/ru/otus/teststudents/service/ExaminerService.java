package ru.otus.teststudents.service;

import ru.otus.teststudents.exceptions.QuestionException;

public interface ExaminerService {


    int exam() throws QuestionException;

    boolean estimationExam(int goodAnswers);

    void publishResult(boolean result);
}
