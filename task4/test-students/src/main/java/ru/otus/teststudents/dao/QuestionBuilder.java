package ru.otus.teststudents.dao;

import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

public interface QuestionBuilder {

    Question build(String[] values) throws QuestionException;
}
