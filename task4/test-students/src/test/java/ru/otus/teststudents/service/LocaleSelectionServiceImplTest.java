package ru.otus.teststudents.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.teststudents.config.LocaleConfig;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Тестирование выбора локали")
@SpringBootTest
class LocaleSelectionServiceImplTest {

    @MockBean
    private ReaderService readerService;

    @Autowired
    private LocaleSelectionService localeSelectionService;

    @Autowired
    private LocaleConfig localeConfig;

    @DisplayName("Выбор локали")
    @Test
    void selectLocaleTest() {
        given(readerService.read()).willReturn("ru");
        Locale expLocale = new Locale("ru", "RU");
        localeSelectionService.selectLocale();
        Locale locale = localeConfig.getCurrentLocale();
        assertEquals(expLocale, locale);

        given(readerService.read()).willReturn("en");
        expLocale = new Locale("en", "EN");
        localeSelectionService.selectLocale();
        locale = localeConfig.getCurrentLocale();
        assertEquals(expLocale, locale);

        given(readerService.read()).willReturn("fr");
        expLocale = new Locale("en", "EN");
        localeSelectionService.selectLocale();
        locale = localeConfig.getCurrentLocale();
        assertEquals(expLocale, locale);
    }

}
