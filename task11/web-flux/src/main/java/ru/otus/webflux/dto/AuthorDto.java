package ru.otus.webflux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.webflux.domain.Author;

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
