package ru.otus.jdbclibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {

    private Long id;
    private String title;
    private Author author;
    private Genre genre;

}
