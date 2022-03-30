package ru.otus.springbatch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.springbatch.domain.mongo.MBook;

public interface BookRepository extends MongoRepository<MBook, String> {

 }
