package ru.otus.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.webflux.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
