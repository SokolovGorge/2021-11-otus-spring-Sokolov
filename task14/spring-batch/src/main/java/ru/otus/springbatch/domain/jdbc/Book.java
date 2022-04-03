package ru.otus.springbatch.domain.jdbc;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Book {

    private Long id;
    private String title;
    private Long authorId;
    private Long genreId;

}
