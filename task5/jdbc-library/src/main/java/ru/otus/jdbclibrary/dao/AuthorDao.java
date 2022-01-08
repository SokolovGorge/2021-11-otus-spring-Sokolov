package ru.otus.jdbclibrary.dao;

import ru.otus.jdbclibrary.domain.Author;

import java.util.List;

public interface AuthorDao {

    long count();

    long insert(Author author);

    void update(Author author);

    void deleteById(long id);

    Author getById(long id);

    List<Author> getAll();

}
