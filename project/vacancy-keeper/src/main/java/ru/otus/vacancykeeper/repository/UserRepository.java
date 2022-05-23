package ru.otus.vacancykeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.vacancykeeper.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
