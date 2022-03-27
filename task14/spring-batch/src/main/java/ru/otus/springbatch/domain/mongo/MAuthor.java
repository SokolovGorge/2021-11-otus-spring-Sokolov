package ru.otus.springbatch.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("author")
public class MAuthor {

    @Id
    private String id;
    private String firstName;
    private String surName;

    public MAuthor(String firstName, String surName) {
        this.firstName = firstName;
        this.surName = surName;
    }

}
