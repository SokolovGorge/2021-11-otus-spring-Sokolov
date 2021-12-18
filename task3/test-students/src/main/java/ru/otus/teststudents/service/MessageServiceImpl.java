package ru.otus.teststudents.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.teststudents.config.LocaleConfig;

@Service
public class MessageServiceImpl implements MessageService {

    private final LocaleConfig localeConfig;
    private final MessageSource messageSource;

    public MessageServiceImpl(LocaleConfig localeConfig, MessageSource messageSource) {
        this.localeConfig = localeConfig;
        this.messageSource = messageSource;
    }


    @Override
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, localeConfig.getCurrentLocale());
    }
}
