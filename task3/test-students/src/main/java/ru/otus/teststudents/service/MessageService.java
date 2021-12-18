package ru.otus.teststudents.service;

import org.springframework.lang.Nullable;

public interface MessageService {

    String getMessage(String code, Object... args);
}
