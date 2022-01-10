package ru.otus.ormlibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.ormlibrary.models.Book;
import ru.otus.ormlibrary.models.Remark;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class BookDto {

    private Long id;
    private String title;
    private AuthorDto author;
    private GenreDto genre;
    private List<String> remarks;

    public BookDto(Book book) {
        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor() == null ? null : new AuthorDto(book.getAuthor());
        genre = book.getGenre() == null ? null : new GenreDto(book.getGenre());
        remarks = book.getRemarks() == null ? null : book.getRemarks().stream().map(Remark::getText).collect(Collectors.toList());
    }
}
