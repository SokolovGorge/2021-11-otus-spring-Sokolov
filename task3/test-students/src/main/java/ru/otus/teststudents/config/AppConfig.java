package ru.otus.teststudents.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "test")
@Component
public class AppConfig {

    private Map<String, String> localeMap;

    private String localeDef;

    private String questionFilename;

    private int passCount;

    public Map<String, String> getLocaleMap() {
        return localeMap;
    }

    public void setLocaleMap(Map<String, String> localeMap) {
        this.localeMap = localeMap;
    }

    public String getLocaleDef() {
        return localeDef;
    }

    public void setLocaleDef(String localeDef) {
        this.localeDef = localeDef;
    }

    public String getQuestionFilename() {
        return questionFilename;
    }

    public void setQuestionFilename(String questionFilename) {
        this.questionFilename = questionFilename;
    }

    public int getPassCount() {
        return passCount;
    }

    public void setPassCount(int passCount) {
        this.passCount = passCount;
    }

}
