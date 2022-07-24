package ru.otus.vacancykeeper.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.vacancykeeper.domain.SUser;

import java.util.List;
import java.util.Optional;

public interface SUserRepository extends JpaRepository<SUser, Long> {

    @EntityGraph("user-role-attribute-entity-graph")
    @Override
    List<SUser> findAll();

    @EntityGraph("user-role-attribute-entity-graph")
    @Override
    Optional<SUser> findById(Long id);

    @EntityGraph("user-role-attribute-entity-graph")
    Optional<SUser> findSUserByLogin(String login);
}
