package ru.otus.jpalibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.jpalibrary.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
