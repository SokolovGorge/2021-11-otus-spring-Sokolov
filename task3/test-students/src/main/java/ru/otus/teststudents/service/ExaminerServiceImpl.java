package ru.otus.teststudents.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.teststudents.config.AppConfig;
import ru.otus.teststudents.dao.QuestionsDao;
import ru.otus.teststudents.domain.Answer;
import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

import java.util.List;
import java.util.Locale;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionsDao questionsDao;
    private final ReaderAnswerService readerAnswerService;
    private final WriterService writerService;
    private final AppConfig appConfig;
    private final LocaleProvider localeProvider;
    private final MessageSource messageSource;

    public ExaminerServiceImpl(QuestionsDao questionsDao,
                               ReaderAnswerService readerAnswerService,
                               WriterService writerService,
                               AppConfig appConfig,
                               LocaleProvider localeProvider,
                               MessageSource messageSource) {
        this.questionsDao = questionsDao;
        this.readerAnswerService = readerAnswerService;
        this.writerService = writerService;
        this.appConfig = appConfig;
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
    }

    @Override
    public void exam() {
        Locale locale = localeProvider.getLocale();
        try {
            int goodAnswersCount = 0;
            printExamStart(locale);
            List<Question> questions = questionsDao.readQuestions(locale);
            for (Question question : questions) {
                printQuestion(question, locale);
                Answer answer = readerAnswerService.request(question, locale);
                if (answer.isCorrect()) {
                    goodAnswersCount++;
                }
            }
            printResult(goodAnswersCount >= appConfig.getPassCount(), locale);
        } catch (QuestionException ex) {
            writerService.println(messageSource.getMessage("info.error", new String[]{ex.getLocalizedMessage()}, locale));
        }
    }

    private void printExamStart(Locale locale) {
        writerService.println("*********************** "+ messageSource.getMessage("info.start-examination", null, locale) + " *********************************");
    }

    private void printQuestion(Question question, Locale locale) {
        writerService.println(messageSource.getMessage("info.question", null, locale) + ": " + question.getQuestion());
        writerService.println(messageSource.getMessage("info.answer-choice", null, locale));
        for (int i = 0; i < question.getAnswers().size(); i++) {
            writerService.println(i + 1 + ". " + question.getAnswers().get(i).getAnswer());
        }
    }

    private void printResult(boolean result, Locale locale) {
        writerService.println("*********************** " + messageSource.getMessage("info.end-examination", null, locale) + " *********************************");
        if (result) {
            writerService.println(messageSource.getMessage("info.result-success", null, locale));
        } else {
            writerService.println(messageSource.getMessage("info.result-fault", null, locale));
        }
        writerService.println("*************************************************************************");
    }


}
