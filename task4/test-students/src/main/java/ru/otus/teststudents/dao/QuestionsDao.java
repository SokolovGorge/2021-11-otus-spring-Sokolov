package ru.otus.teststudents.dao;

import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

import java.util.List;

public interface QuestionsDao {

    List<Question> readQuestions() throws QuestionException;
}
