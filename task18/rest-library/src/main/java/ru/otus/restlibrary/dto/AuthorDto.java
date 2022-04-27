package ru.otus.restlibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.restlibrary.domain.Author;

@AllArgsConstructor
@Data
public class AuthorDto {

    public static final AuthorDto DUMMY = new AuthorDto(0L, "N/A", "N/A");

    private Long id;
    private String firstName;
    private String surName;

    public AuthorDto(Author author) {
        id = author.getId();
        firstName = author.getFirstName();
        surName = author.getSurName();
    }

}
