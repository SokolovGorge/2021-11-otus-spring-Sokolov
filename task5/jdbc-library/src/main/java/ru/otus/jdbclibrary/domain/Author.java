package ru.otus.jdbclibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Author {
    private Long id;
    private String firstName;
    private String surName;

}
