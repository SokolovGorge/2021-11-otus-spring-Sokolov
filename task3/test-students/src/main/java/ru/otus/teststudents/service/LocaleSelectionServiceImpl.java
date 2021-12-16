package ru.otus.teststudents.service;

import org.springframework.stereotype.Service;
import ru.otus.teststudents.config.LocaleConfig;
import ru.otus.teststudents.stereotype.LogEnable;

import java.util.Locale;

@Service
public class LocaleSelectionServiceImpl implements LocaleSelectionService {

    private final WriterService writerService;
    private final ReaderService readerService;
    private final LocaleConfig localeConfig;
    private Locale currentLocale;

    public LocaleSelectionServiceImpl(WriterService writerService, ReaderService readerService, LocaleConfig localeConfig) {
        this.writerService = writerService;
        this.readerService = readerService;
        this.localeConfig = localeConfig;
    }


    @LogEnable
    @Override
    public void selectLocale() {
        writerService.print("Input code locale:");
        String code = readerService.read();
        String codeLang = localeConfig.getLocaleMap().get(code.toLowerCase());
        Locale locale;
        if (null == codeLang) {
            locale = Locale.forLanguageTag(localeConfig.getLocaleDef());
            writerService.println("Unknown locale " + code + ". Set default locale " + locale);
        } else {
            locale = Locale.forLanguageTag(codeLang);
        }
        currentLocale = locale;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
}
