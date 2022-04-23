package ru.otus.restlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.restlibrary.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
