package ru.otus.ormlibrary.service;

import ru.otus.ormlibrary.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto addBook(String title, long authorId, long genreId, List<String> remarks);

    BookDto updateBook(long id, String title, long authorId, long genreId, List<String> remarks);

    BookDto deleteBook(long id);

    BookDto getBook(long id);

}
