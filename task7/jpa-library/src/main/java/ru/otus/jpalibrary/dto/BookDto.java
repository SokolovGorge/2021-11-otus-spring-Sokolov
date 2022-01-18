package ru.otus.jpalibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.jpalibrary.domain.Book;
import ru.otus.jpalibrary.domain.Remark;


import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class BookDto {

    private Long id;
    private String title;
    private AuthorDto author;
    private GenreDto genre;

    public BookDto(Book book) {
        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor() == null ? null : new AuthorDto(book.getAuthor());
        genre = book.getGenre() == null ? null : new GenreDto(book.getGenre());
    }
}
