package ru.otus.teststudents.exceptions;

import com.opencsv.exceptions.CsvValidationException;

public class QuestionException extends Exception {

    public QuestionException() {
        super();
    }

    public QuestionException(String message) {
        super(message);
    }

    public QuestionException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionException(Throwable cause) {
        super(cause);
    }
}
