package ru.otus.vacancykeeper.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.vacancykeeper.domain.Vacancy;

public interface VacancyService {

    Vacancy findById(Long id);

    Vacancy save(Vacancy vacancy);

    void deleteById(Long id);

    Page<Vacancy> findVacancyByParameters(Long taskId, String name, Pageable pageable);

}
