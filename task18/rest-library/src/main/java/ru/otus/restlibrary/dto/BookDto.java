package ru.otus.restlibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.restlibrary.dto.AuthorDto;
import ru.otus.restlibrary.dto.GenreDto;
import ru.otus.restlibrary.domain.Book;

@AllArgsConstructor
@Data
public class BookDto {

    public static final BookDto DUMMY = new BookDto(0L, "N/A", AuthorDto.DUMMY, GenreDto.DUMMY);

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
