package ru.otus.teststudents.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final LocaleSelectionService localeSelection;
    private final MessageSource messageSource;

    public MessageServiceImpl(LocaleSelectionService localeSelection, MessageSource messageSource) {
        this.localeSelection = localeSelection;
        this.messageSource = messageSource;
    }


    @Override
    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, localeSelection.getCurrentLocale());
    }
}
