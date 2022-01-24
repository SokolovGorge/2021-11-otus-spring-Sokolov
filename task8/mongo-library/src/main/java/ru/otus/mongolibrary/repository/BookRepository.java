package ru.otus.mongolibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mongolibrary.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    List<Book> findByTitle(String title);

    List<Book> findByAuthorSurName(String surname);

    List<Book> findByGenreName(String name);
}
