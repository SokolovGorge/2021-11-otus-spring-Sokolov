package ru.otus.weblibrary.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.weblibrary.dto.AuthorDto;
import ru.otus.weblibrary.dto.BookDto;
import ru.otus.weblibrary.dto.GenreDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class BookEditModel {

    private long id;

    @NotBlank(message = "Заголовок должен быть заполнен")
    private String title;

    @NotNull(message = "Автор должен быть заполнен")
    private Long authorId;

    @NotNull(message = "Жанр должен быть заполнен")
    private Long genreId;

    public BookEditModel(BookDto book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authorId = book.getAuthor() == null ? null : book.getAuthor().getId();
        this.genreId = book.getGenre() == null ? null : book.getGenre().getId();
    }

}
