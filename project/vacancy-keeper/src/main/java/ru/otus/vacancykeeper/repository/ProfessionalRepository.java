package ru.otus.vacancykeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.vacancykeeper.domain.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

}
