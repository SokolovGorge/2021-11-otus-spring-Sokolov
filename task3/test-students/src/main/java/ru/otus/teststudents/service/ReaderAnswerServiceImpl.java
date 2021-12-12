package ru.otus.teststudents.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.teststudents.domain.Answer;
import ru.otus.teststudents.domain.Question;

import java.util.Locale;

@Service
public class ReaderAnswerServiceImpl implements ReaderAnswerService {

    private final ReaderService readerService;
    private final WriterService writerService;
    private final MessageSource messageSource;

    public ReaderAnswerServiceImpl(ReaderService readerService, WriterService writerService, MessageSource messageSource) {
        this.readerService = readerService;
        this.writerService = writerService;
        this.messageSource = messageSource;
    }

    @Override
    public Answer request(Question question, Locale locale) {
        writerService.print(messageSource.getMessage("prompt.enter-number", null, locale) + ":");
        int choice = readAnswer(question);
        if (choice < 0) {
            do {
                writerService.print(messageSource.getMessage("prompt.invalid-choice", null, locale) + ":");
                choice = readAnswer(question);
            } while (choice < 0);
        }
        return question.getAnswers().get(choice - 1);
    }

    private int readAnswer(Question question) {
        String answer = readerService.read();
        try {
            int choice = Integer.parseInt(answer);
            if (choice > 0 && choice <= question.getAnswers().size()) {
                return choice;
            }
            return -1;
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

}
