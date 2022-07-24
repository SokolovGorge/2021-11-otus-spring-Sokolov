package ru.otus.vacancykeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDto {

    private String username;
    private String password;

}
