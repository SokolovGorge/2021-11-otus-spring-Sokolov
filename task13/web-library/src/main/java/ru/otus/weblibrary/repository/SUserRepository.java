package ru.otus.weblibrary.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.weblibrary.domain.SUser;

public interface SUserRepository extends JpaRepository<SUser, Long> {

    @EntityGraph("user-roles-entity-graph")
    SUser findSUserByLogin(String login);

}
