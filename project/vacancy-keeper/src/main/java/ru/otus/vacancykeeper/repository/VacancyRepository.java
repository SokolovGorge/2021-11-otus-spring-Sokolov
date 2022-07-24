package ru.otus.vacancykeeper.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT l.vacancy FROM VacancyLink l WHERE " +
            "l.task.id = :taskId AND " +
            "(:name IS NULL OR :name = '' OR LOWER(l.vacancy.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Vacancy> findVacancyByParameters(@Param("taskId") Long taskId, @Param("name") String name, Pageable pageable);

}
