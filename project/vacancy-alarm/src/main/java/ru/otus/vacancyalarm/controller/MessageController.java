package ru.otus.vacancyalarm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.vacancyalarm.dto.MessageInfo;

@RestController
@RequestMapping("/api")
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @PostMapping("/send")
    public ResponseEntity sendMessage(@RequestBody MessageInfo info) {
        log.info(generateMessage(info));
        return ResponseEntity.ok().build();
    }

    private String generateMessage(MessageInfo info) {
        final StringBuilder sb = new StringBuilder("Уважаемый ")
                .append(info.getFirstname());
        if (info.getPathname() != null) {
            sb.append(" ")
                    .append(info.getPathname());
        }
        sb.append(" ")
                .append(info.getSurname())
                .append("\n")
                .append("Поступила новая вакансия ")
                .append(info.getVacancy());
        if (info.getSourceUrl() != null) {
            sb.append("\n")
                    .append(info.getSourceUrl());
        }
        return sb.toString();
    }

}
