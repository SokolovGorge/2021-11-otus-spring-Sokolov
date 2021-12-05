package ru.otus.teststudents.service;

import java.io.InputStream;
import java.util.Scanner;

public class ReaderServiceImpl implements ReaderService {

    private final Scanner scanner;

    public ReaderServiceImpl(InputStream input) {
        this.scanner = new Scanner(input);
    }

    @Override
    public String read() {
        return scanner.next();
    }
}
