package ru.otus.teststudents.service;

import org.springframework.stereotype.Service;
import ru.otus.teststudents.domain.Question;

@Service
public class ReaderAnswerServiceImpl implements ReaderAnswerService {

    private final ReaderService readerService;
    private final WriterService writerService;

    public ReaderAnswerServiceImpl(ReaderService readerService, WriterService writerService) {
        this.readerService = readerService;
        this.writerService = writerService;
    }

    @Override
    public String request(Question question) {
        writerService.print("Enter number of choice:");
        int choice = readAnswer(question);
        if (choice < 0) {
            do {
                writerService.print("Invalid choice, try again:");
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
