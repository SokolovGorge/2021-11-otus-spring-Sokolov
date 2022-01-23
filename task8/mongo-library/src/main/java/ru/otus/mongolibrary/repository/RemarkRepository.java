package ru.otus.mongolibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mongolibrary.domain.Book;
import ru.otus.mongolibrary.domain.Remark;

import java.util.List;

public interface RemarkRepository extends MongoRepository<Remark, String> {

    List<Remark> findAllRemarksByBook(Book book);
}
