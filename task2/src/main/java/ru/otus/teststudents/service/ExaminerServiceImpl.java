package ru.otus.teststudents.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.teststudents.dao.ReaderQuestionsDao;
import ru.otus.teststudents.domain.Question;

import java.util.List;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private ReaderQuestionsDao readerQuestionsDao;
    private ReaderAnswerService readerAnswerService;
    private PublisherService publisherService;
    private int passingCount;

    public ExaminerServiceImpl(ReaderQuestionsDao readerQuestionsDao,
                               ReaderAnswerService readerAnswerDao,
                               PublisherService publisherDao,
                               @Value("${test.passCount}") int passingCount) {
        this.readerQuestionsDao = readerQuestionsDao;
        this.readerAnswerService = readerAnswerDao;
        this.publisherService = publisherDao;
        this.passingCount = passingCount;
    }

    @Override
    public int exam() {
        int goodAnswersCount = 0;
        publisherService.publishStart();
        List<Question> questions = readerQuestionsDao.readQuestions();
        for (Question question : questions) {
            publisherService.publishQuestion(question);
            String answer = readerAnswerService.request(question);
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
        publisherService.publishResult(result);
    }

}
