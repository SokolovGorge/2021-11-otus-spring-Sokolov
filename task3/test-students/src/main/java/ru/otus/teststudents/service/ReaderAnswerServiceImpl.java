package ru.otus.teststudents.service;

import org.springframework.stereotype.Service;
import ru.otus.teststudents.domain.Answer;
import ru.otus.teststudents.domain.Question;

@Service
public class ReaderAnswerServiceImpl implements ReaderAnswerService {

    private final ReaderService readerService;
    private final MessageService messageService;
    private final WriterService writerService;


    public ReaderAnswerServiceImpl(ReaderService readerService, WriterService writerService, MessageService messageService) {
        this.readerService = readerService;
        this.writerService = writerService;
        this.messageService = messageService;
    }

    @Override
    public Answer request(Question question) {
        writerService.print(messageService.getMessage("prompt.enter-number", null) + ":");
        int choice = readAnswer(question);
        if (choice < 0) {
            do {
                writerService.print(messageService.getMessage("prompt.invalid-choice", null) + ":");
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
