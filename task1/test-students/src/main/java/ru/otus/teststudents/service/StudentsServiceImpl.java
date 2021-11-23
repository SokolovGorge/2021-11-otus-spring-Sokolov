package ru.otus.teststudents.service;

import ru.otus.teststudents.dao.PublisherQuestionsDao;
import ru.otus.teststudents.dao.ReaderQuestionsDao;
import ru.otus.teststudents.domain.Question;

import java.util.List;

public class StudentsServiceImpl implements StudentsService {

    private final ReaderQuestionsDao readerQuestionsDao;
    private final PublisherQuestionsDao publisherQuestionsDao;

    private List<Question> questions;

    public StudentsServiceImpl(ReaderQuestionsDao readerQuestionsDao, PublisherQuestionsDao publisherQuestionsDao) {
        this.readerQuestionsDao = readerQuestionsDao;
        this.publisherQuestionsDao = publisherQuestionsDao;
    }

    @Override
    public void prepareQuestions() {
        questions = readerQuestionsDao.readQuestions();
    }

    @Override
    public void publishQuestions() {
        publisherQuestionsDao.publishQuestions(questions);
    }

}
