package ru.otus.teststudents.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.teststudents.dao.QuestionsDao;
import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

import java.util.List;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionsDao questionsDao;
    private final ReaderAnswerService readerAnswerService;
    private final PrinterQuestionService printerQuestionService;
    private final int passingCount;

    public ExaminerServiceImpl(QuestionsDao questionsDao,
                               ReaderAnswerService readerAnswerService,
                               PrinterQuestionService printerQuestionService,
                               @Value("${test.passCount}") int passingCount) {
        this.questionsDao = questionsDao;
        this.readerAnswerService = readerAnswerService;
        this.printerQuestionService = printerQuestionService;
        this.passingCount = passingCount;
    }

    @Override
    public int exam() throws QuestionException {
        int goodAnswersCount = 0;
        printerQuestionService.printExamStart();
        List<Question> questions = questionsDao.readQuestions();
        for (Question question : questions) {
            printerQuestionService.printQuestion(question);
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
        printerQuestionService.printResult(result);
    }

}
