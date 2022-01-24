package ru.otus.mongolibrary.service;

import ru.otus.mongolibrary.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto addBook(String title, String authorId, String genreId);

    BookDto updateBook(String id, String title, String authorId, String genreId);

    BookDto deleteBook(String id);

    BookDto getBook(String id);

}
