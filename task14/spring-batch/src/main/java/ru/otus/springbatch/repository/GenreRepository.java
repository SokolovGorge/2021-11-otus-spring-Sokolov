package ru.otus.springbatch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.springbatch.domain.mongo.MGenre;

public interface GenreRepository extends MongoRepository<MGenre, String> {

}
