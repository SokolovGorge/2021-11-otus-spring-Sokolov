package ru.otus.springbatch.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("book")
public class MBook {

    @Id
    private String id;
    private String title;
    private MAuthor author;
    private MGenre genre;

    public MBook(String title, MAuthor author, MGenre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

}
