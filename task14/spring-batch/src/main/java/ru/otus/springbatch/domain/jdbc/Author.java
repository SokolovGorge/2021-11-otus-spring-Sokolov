package ru.otus.springbatch.domain.jdbc;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Author {

    private Long id;
    private String firstName;
    private String surName;

}
