package ru.otus.teststudents.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.teststudents.dao.QuestionsDao;
import ru.otus.teststudents.domain.Answer;
import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

import java.util.List;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionsDao questionsDao;
    private final ReaderAnswerService readerAnswerService;
    private final WriterService writerService;
    private final int passingCount;

    public ExaminerServiceImpl(QuestionsDao questionsDao,
                               ReaderAnswerService readerAnswerService,
                               WriterService writerService, @Value("${test.passCount}") int passingCount) {
        this.questionsDao = questionsDao;
        this.readerAnswerService = readerAnswerService;
        this.writerService = writerService;
        this.passingCount = passingCount;
    }

    @Override
    public void exam() {
        try {
            int goodAnswersCount = 0;
            printExamStart();
            List<Question> questions = questionsDao.readQuestions();
            for (Question question : questions) {
                printQuestion(question);
                Answer answer = readerAnswerService.request(question);
                if (answer.isCorrect()) {
                    goodAnswersCount++;
                }
            }
            printResult(goodAnswersCount >= passingCount);
        } catch (QuestionException ex) {
            writerService.println("An error occurred while completing the task: " + ex.getLocalizedMessage());
        }
    }

    private void printExamStart() {
        writerService.println("*********************** Start examination *********************************");
    }

    private void printQuestion(Question question) {
        writerService.println("Question: " + question.getQuestion());
        writerService.println("Answer choice");
        for (int i = 0; i < question.getAnswers().size(); i++) {
            writerService.println(i + 1 + ". " + question.getAnswers().get(i).getAnswer());
        }
    }

    private void printResult(boolean result) {
        writerService.println("*********************** End examination *********************************");
        if (result) {
            writerService.println("You have successfully passed the exam!");
        } else {
            writerService.println("You didn't pass the exam. Try again.");
        }
        writerService.println("*************************************************************************");
    }


}
