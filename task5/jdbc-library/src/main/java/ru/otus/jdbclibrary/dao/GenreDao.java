package ru.otus.jdbclibrary.dao;

import ru.otus.jdbclibrary.domain.Genre;

import java.util.List;

public interface GenreDao {

    long count();

    long insert(Genre genre);

    void update(Genre genre);

    void deleteById(long id);

    Genre getById(long id);

    List<Genre> getAll();

}
