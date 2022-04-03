package ru.otus.springbatch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.springbatch.domain.mongo.MRemark;

public interface RemarkRepository extends MongoRepository<MRemark, String> {

}
