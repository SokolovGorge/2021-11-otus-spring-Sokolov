package ru.otus.weblibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.weblibrary.domain.Book;

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
