package ru.otus.ormlibrary.repositories;

import ru.otus.ormlibrary.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    long count();

    Genre save(Genre genre);

    void deleteById(long id);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

}
