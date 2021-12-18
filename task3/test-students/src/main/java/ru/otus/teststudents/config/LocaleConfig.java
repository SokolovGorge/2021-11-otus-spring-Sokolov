package ru.otus.teststudents.config;

import java.util.Locale;
import java.util.Map;

public interface LocaleConfig {

    Map<String, String> getLocaleMap();

    String getLocaleDef();

    Locale getCurrentLocale();

    void setCurrentLocale(Locale locale);

    String getQuestionFileName();
}
