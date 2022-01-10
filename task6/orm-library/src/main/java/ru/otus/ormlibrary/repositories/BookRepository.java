package ru.otus.ormlibrary.repositories;

import ru.otus.ormlibrary.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    long count();

    Book save(Book book);

    void deleteById(long id);

    Optional<Book> findById(long id);

    List<Book> findAll();
}
