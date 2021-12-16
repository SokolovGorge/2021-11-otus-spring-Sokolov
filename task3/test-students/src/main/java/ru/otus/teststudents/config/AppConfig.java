package ru.otus.teststudents.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "test")
@Component
public class AppConfig implements QuestionConfig, LocaleConfig, ExamConfig {

    private Map<String, String> localeMap;

    private String localeDef;

    private String questionFilename;

    private int passCount;

    @Override
    public Map<String, String> getLocaleMap() {
        return localeMap;
    }

    public void setLocaleMap(Map<String, String> localeMap) {
        this.localeMap = localeMap;
    }

    @Override
    public String getLocaleDef() {
        return localeDef;
    }

    public void setLocaleDef(String localeDef) {
        this.localeDef = localeDef;
    }

    @Override
    public String getQuestionFilename() {
        return questionFilename;
    }

    public void setQuestionFilename(String questionFilename) {
        this.questionFilename = questionFilename;
    }

    @Override
    public int getPassCount() {
        return passCount;
    }

    public void setPassCount(int passCount) {
        this.passCount = passCount;
    }

}
