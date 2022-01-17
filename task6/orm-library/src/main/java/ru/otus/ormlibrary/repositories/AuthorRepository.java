package ru.otus.ormlibrary.repositories;

import ru.otus.ormlibrary.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    long count();

    Author save(Author author);

    void deleteById(long id);

    Optional<Author> findById(long id);

    List<Author> findAll();
}
