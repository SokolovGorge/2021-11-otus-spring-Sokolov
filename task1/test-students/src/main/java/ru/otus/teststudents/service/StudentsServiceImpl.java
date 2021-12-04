package ru.otus.teststudents.service;

import ru.otus.teststudents.dao.ReaderQuestionsDao;
import ru.otus.teststudents.domain.Question;

import java.util.List;

public class StudentsServiceImpl implements StudentsService {

    private final ReaderQuestionsDao readerQuestionsDao;
    private final PublisherQuestionsService publisherQuestionsService;

    private List<Question> questions;

    public StudentsServiceImpl(ReaderQuestionsDao readerQuestionsDao, PublisherQuestionsService publisherQuestionsService) {
        this.readerQuestionsDao = readerQuestionsDao;
        this.publisherQuestionsService = publisherQuestionsService;
    }

    @Override
    public void prepareQuestions() {
        questions = readerQuestionsDao.readQuestions();
    }

    @Override
    public void publishQuestions() {
        publisherQuestionsService.publishQuestions(questions);
    }

}
