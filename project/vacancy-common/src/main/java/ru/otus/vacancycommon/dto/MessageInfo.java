package ru.otus.vacancycommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageInfo {

    private String firstname;
    private String surname;
    private String pathname;
    private String email;
    private String telephone;
    private String vacancy;
    private String sourceUrl;

}
