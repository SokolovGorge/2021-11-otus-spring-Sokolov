package ru.otus.jdbclibrary.service;

import ru.otus.jdbclibrary.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book addBook(String title, long authorId, long genreId);

    Book updateBook(long id, String title, long authorId, long genreId);

    Book deleteBook(long id);

    Book getBook(long id);

}
