package ru.otus.vacancykeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.vacancykeeper.domain.Area;

public interface AreaRepository extends JpaRepository<Area, Long> {

}
