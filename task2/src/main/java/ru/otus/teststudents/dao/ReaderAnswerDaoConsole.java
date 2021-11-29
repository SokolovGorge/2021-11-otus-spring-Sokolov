package ru.otus.teststudents.dao;

import org.springframework.stereotype.Component;
import ru.otus.teststudents.domain.Question;

import java.util.Scanner;

@Component
public class ReaderAnswerDaoConsole implements ReaderAnswerDao {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String request(Question question) {
        System.out.print("Enter number of choice:");
        int choice = readAnswer(question);
        if (choice < 0) {
            do {
                System.out.print("Invalid choice, try again:");
                choice = readAnswer(question);
            } while (choice < 0);
        }
        return question.getAnswers().get(choice - 1);
    }

    private int readAnswer(Question question) {
        String answer = scanner.next();
        try {
            int choice = Integer.valueOf(answer);
            if (choice > 0 && choice <= question.getAnswers().size()) {
                return choice;
            }
            return -1;
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

}
