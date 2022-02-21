package ru.otus.webflux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.webflux.domain.Book;

@AllArgsConstructor
@Data
public class BookDto {

    private String id;
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
