package ru.otus.vacancykeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.vacancykeeper.domain.Task;
import ru.otus.vacancykeeper.domain.Vacancy;
import ru.otus.vacancykeeper.domain.VacancyLink;

public interface VacancyLinkRepository extends JpaRepository<VacancyLink, Long> {

    VacancyLink getVacancyLinkByTaskAndVacancy(Task task, Vacancy vacancy);
}
