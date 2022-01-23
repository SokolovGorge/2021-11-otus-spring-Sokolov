package ru.otus.mongolibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.mongolibrary.domain.Author;

@AllArgsConstructor
@Data
public class AuthorDto {

    private String id;
    private String firstName;
    private String surName;

    public AuthorDto(Author author) {
        id = author.getId();
        firstName = author.getFirstName();
        surName = author.getSurName();
    }

}
