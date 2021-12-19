package ru.otus.teststudents.config;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Map;

@Component
public class LocaleConfigImpl implements LocaleConfig {

    private final AppConfig appConfig;

    private Locale currentLocale;

    public LocaleConfigImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public Map<String, String> getLocaleMap() {
        return appConfig.getLocaleMap();
    }

    @Override
    public String getLocaleDef() {
        return appConfig.getLocaleDef();
    }

    @Override
    public Locale getCurrentLocale() {
        return currentLocale;
    }

    @Override
    public void setCurrentLocale(Locale locale) {
        this.currentLocale = locale;
    }

}
