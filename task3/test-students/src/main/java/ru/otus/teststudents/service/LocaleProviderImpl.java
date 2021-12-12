package ru.otus.teststudents.service;

import org.springframework.stereotype.Service;
import ru.otus.teststudents.config.AppConfig;
import ru.otus.teststudents.stereotype.LogEnable;

import java.util.Locale;

@Service
public class LocaleProviderImpl implements  LocaleProvider {

    private final WriterService writerService;
    private final ReaderService readerService;
    private final AppConfig appConfig;

    public LocaleProviderImpl(WriterService writerService, ReaderService readerService, AppConfig appConfig) {
        this.writerService = writerService;
        this.readerService = readerService;
        this.appConfig = appConfig;
    }


    @LogEnable
    @Override
    public Locale getLocale() {
        writerService.print("Input code locale:");
        String code = readerService.read();
        String codeLang = appConfig.getLocaleMap().get(code.toLowerCase());
        Locale locale;
        if (null == codeLang) {
            locale = Locale.forLanguageTag(appConfig.getLocaleDef());
            writerService.println("Unknown locale " + code + ". Set default locale " + locale);
        } else {
            locale = Locale.forLanguageTag(codeLang);
        }
        return locale;
    }
}
