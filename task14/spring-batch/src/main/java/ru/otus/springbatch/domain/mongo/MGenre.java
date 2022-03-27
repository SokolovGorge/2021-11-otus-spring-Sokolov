package ru.otus.springbatch.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("genre")
public class MGenre {

    @Id
    private String id;
    private String name;

    public MGenre(String name) {
        this.name = name;
    }

}
