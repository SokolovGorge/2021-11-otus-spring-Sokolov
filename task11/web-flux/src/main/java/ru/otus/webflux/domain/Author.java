package ru.otus.webflux.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Author {

    @Id
    private String id;
    private String firstName;
    private String surName;

    public Author(String firstName, String surName) {
        this.firstName = firstName;
        this.surName = surName;
    }
}
