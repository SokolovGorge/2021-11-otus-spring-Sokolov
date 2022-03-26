package ru.otus.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.weblibrary.domain.AclObjectIdentity;

public interface AclObjectIdentityRepository extends JpaRepository<AclObjectIdentity, Long> {

    void removeByObjectIdClassAndObjectIdIdentity(Long objectIdClass, Long objectIdIdentity);
}
