package ru.otus.teststudents.dao;

import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

import java.util.List;
import java.util.Locale;

public interface QuestionsDao {

    List<Question> readQuestions(Locale locale) throws QuestionException;
}
