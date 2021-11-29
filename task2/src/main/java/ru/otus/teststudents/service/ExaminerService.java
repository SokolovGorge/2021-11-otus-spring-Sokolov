package ru.otus.teststudents.service;

public interface ExaminerService {

    int exam();

    boolean estimationExam(int goodAnswers);

    void publishResult(boolean result);
}
