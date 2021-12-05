package ru.otus.teststudents.service;

import org.springframework.stereotype.Service;
import ru.otus.teststudents.domain.Question;

@Service
public class PrinterQuestionServiceImpl implements PrinterQuestionService {

    private final WriterService writer;

    public PrinterQuestionServiceImpl(WriterService writer) {
        this.writer = writer;
    }

    @Override
    public void printExamStart() {
        writer.println("*********************** Start examination *********************************");
    }

    @Override
    public void printQuestion(Question question) {
        writer.println("Question: " + question.getQuestion());
        writer.println("Answer choice");
        for (int i = 0; i < question.getAnswers().size(); i++) {
            writer.println(i + 1 + ". " + question.getAnswers().get(i));
        }
    }

    @Override
    public void printResult(boolean result) {
        writer.println("*********************** End examination *********************************");
        if (result) {
            writer.println("You have successfully passed the exam!");
        } else {
            writer.println("You didn't pass the exam. Try again.");
        }
        writer.println("*************************************************************************");
    }
}
