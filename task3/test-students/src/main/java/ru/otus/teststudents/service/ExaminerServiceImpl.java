package ru.otus.teststudents.service;

import org.springframework.stereotype.Service;
import ru.otus.teststudents.config.ExamConfig;
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
    private final MessageService messageService;
    private final LocaleSelectionService localeSelection;
    private final ExamConfig examConfig;

    public ExaminerServiceImpl(QuestionsDao questionsDao,
                               ReaderAnswerService readerAnswerService,
                               WriterService writerService,
                               ExamConfig examConfig,
                               MessageService messageService,
                               LocaleSelectionService localeSelection) {
        this.questionsDao = questionsDao;
        this.readerAnswerService = readerAnswerService;
        this.writerService = writerService;
        this.examConfig = examConfig;
        this.messageService = messageService;
        this.localeSelection = localeSelection;
    }

    @Override
    public void exam() {
        localeSelection.selectLocale();
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
            printResult(goodAnswersCount >= examConfig.getPassCount());
        } catch (QuestionException ex) {
            writerService.println(messageService.getMessage("info.error", new String[]{ex.getLocalizedMessage()}));
        }
    }

    private void printExamStart() {
        writerService.println("*********************** " + messageService.getMessage("info.start-examination", null) + " *********************************");
    }

    private void printQuestion(Question question) {
        writerService.println(messageService.getMessage("info.question", null) + ": " + question.getQuestion());
        writerService.println(messageService.getMessage("info.answer-choice", null));
        for (int i = 0; i < question.getAnswers().size(); i++) {
            writerService.println(i + 1 + ". " + question.getAnswers().get(i).getAnswer());
        }
    }

    private void printResult(boolean result) {
        writerService.println("*********************** " + messageService.getMessage("info.end-examination", null) + " *********************************");
        if (result) {
            writerService.println(messageService.getMessage("info.result-success", null));
        } else {
            writerService.println(messageService.getMessage("info.result-fault", null));
        }
        writerService.println("*************************************************************************");
    }


}
