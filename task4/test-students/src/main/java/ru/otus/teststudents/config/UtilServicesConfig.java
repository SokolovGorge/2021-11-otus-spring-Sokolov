package ru.otus.teststudents.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.teststudents.service.ReaderService;
import ru.otus.teststudents.service.ReaderServiceImpl;
import ru.otus.teststudents.service.WriterService;
import ru.otus.teststudents.service.WriterServiceImpl;

@Configuration
public class UtilServicesConfig {

    @Bean
    public WriterService writerService() {
        return new WriterServiceImpl(System.out);
    }

    @Bean
    public ReaderService readerService() {
        return new ReaderServiceImpl(System.in);
    }

}
