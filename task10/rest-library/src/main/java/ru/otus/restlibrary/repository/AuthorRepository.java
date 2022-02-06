package ru.otus.restlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.restlibrary.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
