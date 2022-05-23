package ru.otus.vacancykeeper.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.vacancykeeper.domain.Task;
import ru.otus.vacancykeeper.domain.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @EntityGraph("task-all-attribute-entity-graph")
    List<Task> findAll();

    @EntityGraph("task-all-attribute-entity-graph")
    List<Task> findTasksByUserOrderByTitle(User user);

    @EntityGraph("task-all-attribute-entity-graph")
    @Query("SELECT t FROM Task t WHERE " +
    "t.user = :user AND (:title IS NULL OR :title = '' OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
    "ORDER BY t.title")
    List<Task> findTasksByUserAndTitle(@Param("user") User user, @Param("title") String title);

}
