package ru.otus.mongolibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.mongolibrary.domain.Genre;

@AllArgsConstructor
@Data
public class GenreDto {

    private String id;
    private String name;

    public GenreDto(Genre genre) {
        id = genre.getId();
        name = genre.getName();
    }
}

