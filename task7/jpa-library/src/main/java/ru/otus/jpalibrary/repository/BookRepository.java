package ru.otus.jpalibrary.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.jpalibrary.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findAll();

    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findByTitle(String title);

    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findByAuthorSurName(String surname);

    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findByGenreName(String name);
}
