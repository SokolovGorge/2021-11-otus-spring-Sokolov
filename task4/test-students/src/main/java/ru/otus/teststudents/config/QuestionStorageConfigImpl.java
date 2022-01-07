package ru.otus.teststudents.config;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Component
public class QuestionStorageConfigImpl implements QuestionStorageConfig {

    private final LocaleConfig localeConfig;
    private final AppConfig appConfig;

    public QuestionStorageConfigImpl(LocaleConfig localeConfig, AppConfig appConfig) {
        this.localeConfig = localeConfig;
        this.appConfig = appConfig;
    }

    @Override
    public String getQuestionFileName() {
        String fileName = appConfig.getQuestionFilename();
        Locale currentLocale = localeConfig.getCurrentLocale();
        if (null == currentLocale || null == currentLocale.getLanguage() || null == currentLocale.getCountry()) {
            return fileName;
        }
        return StringUtils.stripFilenameExtension(fileName) + "_" + currentLocale.getLanguage() + "_" + currentLocale.getCountry() + "." + StringUtils.getFilenameExtension(fileName);
    }

}
