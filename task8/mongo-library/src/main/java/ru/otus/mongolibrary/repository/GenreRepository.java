package ru.otus.mongolibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mongolibrary.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
