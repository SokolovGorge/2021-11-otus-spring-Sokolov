package ru.otus.springbatch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.springbatch.domain.mongo.MAuthor;

public interface AuthorRepository extends MongoRepository<MAuthor, String> {
}
