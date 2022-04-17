package ru.otus.restlibrary.exceptions;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }
}
