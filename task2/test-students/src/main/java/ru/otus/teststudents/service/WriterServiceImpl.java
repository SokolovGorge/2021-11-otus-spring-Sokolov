package ru.otus.teststudents.service;

import java.io.PrintStream;

public class WriterServiceImpl implements WriterService {

    private final PrintStream out;

    public WriterServiceImpl(PrintStream out) {
        this.out = out;
    }

    @Override
    public void println(String x) {
        out.println(x);
    }

    @Override
    public void print(String x) {
        out.print(x);
    }
}
