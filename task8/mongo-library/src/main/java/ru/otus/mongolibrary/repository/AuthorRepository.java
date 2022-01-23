package ru.otus.mongolibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mongolibrary.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
