package ru.otus.jpalibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.jpalibrary.domain.Author;

@AllArgsConstructor
@Data
public class AuthorDto {

    private Long id;
    private String firstName;
    private String surName;

    public AuthorDto(Author author) {
        id = author.getId();
        firstName = author.getFirstName();
        surName = author.getSurName();
    }

}
