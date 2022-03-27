package ru.otus.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.weblibrary.domain.AclSid;

import java.util.List;

public interface AclSidRepository extends JpaRepository<AclSid, Long> {

    List<AclSid> getAclSidBySid(String sid);
}
