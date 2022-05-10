package ru.otus.vacancykeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.vacancykeeper.domain.Task;
import ru.otus.vacancykeeper.domain.Vacancy;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    @Query("select vl.vacancy from VacancyLink vl where vl.task = :task")
    List<Vacancy> getVacanciesByTask(@Param("task") Task task);

    Vacancy findVacancyByScodeAndSid(String scode, String sid);

}
