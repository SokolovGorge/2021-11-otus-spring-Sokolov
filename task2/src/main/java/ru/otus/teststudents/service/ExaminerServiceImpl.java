package ru.otus.teststudents.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.teststudents.dao.PublisherDao;
import ru.otus.teststudents.dao.ReaderAnswerDao;
import ru.otus.teststudents.dao.ReaderQuestionsDao;
import ru.otus.teststudents.domain.Question;

import java.util.List;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private ReaderQuestionsDao readerQuestionsDao;
    private ReaderAnswerDao readerAnswerDao;
    private PublisherDao publisherDao;
    private int passingCount;

    public ExaminerServiceImpl(ReaderQuestionsDao readerQuestionsDao,
                               ReaderAnswerDao readerAnswerDao,
                               PublisherDao publisherDao,
                               @Value("${test.passCount}") int passingCount) {
        this.readerQuestionsDao = readerQuestionsDao;
        this.readerAnswerDao = readerAnswerDao;
        this.publisherDao = publisherDao;
        this.passingCount = passingCount;
    }

    @Override
    public int exam() {
        int goodAnswersCount = 0;
        publisherDao.publishStart();
        List<Question> questions = readerQuestionsDao.readQuestions();
        for (Question question : questions) {
            publisherDao.publishQuestion(question);
            String answer = readerAnswerDao.request(question);
            if (question.getRightAnswer().equals(answer)) {
                goodAnswersCount++;
            }
        }
        return goodAnswersCount;
    }

    @Override
    public boolean estimationExam(int goodAnswers) {
        return goodAnswers >= passingCount;
    }


    @Override
    public void publishResult(boolean result) {
        publisherDao.publishResult(result);
    }

}
