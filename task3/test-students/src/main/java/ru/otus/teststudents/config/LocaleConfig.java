package ru.otus.teststudents.config;

import java.util.Map;

public interface LocaleConfig {

    Map<String, String> getLocaleMap();

    String getLocaleDef();
}
