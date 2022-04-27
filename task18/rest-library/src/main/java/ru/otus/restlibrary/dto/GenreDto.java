package ru.otus.restlibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.restlibrary.domain.Genre;

@AllArgsConstructor
@Data
public class GenreDto {

    public static final GenreDto DUMMY = new GenreDto(0L, "N/A");

    private Long id;
    private String name;

    public GenreDto(Genre genre) {
        id = genre.getId();
        name = genre.getName();
    }
}
