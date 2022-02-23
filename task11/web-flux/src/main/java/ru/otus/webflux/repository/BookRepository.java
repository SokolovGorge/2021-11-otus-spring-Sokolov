package ru.otus.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.webflux.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
