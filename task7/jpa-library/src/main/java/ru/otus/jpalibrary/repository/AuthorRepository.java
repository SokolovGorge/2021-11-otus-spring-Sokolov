package ru.otus.jpalibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.jpalibrary.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
