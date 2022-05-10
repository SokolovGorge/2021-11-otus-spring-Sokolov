package ru.otus.vacancykeeper.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.vacancykeeper.domain.Task;
import ru.otus.vacancykeeper.domain.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @EntityGraph("task-all-attribute-entity-graph")
    List<Task> findAll();

    @EntityGraph("task-all-attribute-entity-graph")
    List<Task> findTasksByUser(User user);

}
