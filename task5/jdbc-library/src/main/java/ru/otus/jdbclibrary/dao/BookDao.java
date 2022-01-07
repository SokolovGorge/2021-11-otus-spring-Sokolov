package ru.otus.jdbclibrary.dao;

import ru.otus.jdbclibrary.domain.Book;

import java.util.List;

public interface BookDao {

    long count();

    long insert(Book book);

    void update(Book book);

    void deleteById(long id);

    Book getById(long id);

    List<Book> getAll();

}
