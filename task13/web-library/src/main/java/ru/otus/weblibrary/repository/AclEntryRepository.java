package ru.otus.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.weblibrary.domain.AclEntry;

public interface AclEntryRepository extends JpaRepository<AclEntry, Long> {
}
