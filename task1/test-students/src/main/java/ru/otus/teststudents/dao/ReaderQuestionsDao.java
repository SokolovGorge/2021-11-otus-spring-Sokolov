package ru.otus.teststudents.dao;

import ru.otus.teststudents.domain.Question;

import java.util.List;

public interface ReaderQuestionsDao {

    List<Question> readQuestions();
}
