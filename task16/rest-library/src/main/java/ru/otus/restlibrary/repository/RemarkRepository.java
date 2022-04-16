package ru.otus.restlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.restlibrary.domain.Remark;

import java.util.List;

@RepositoryRestResource(path = "remarks")
public interface RemarkRepository extends JpaRepository<Remark, Long> {

    List<Remark> findAllRemarksByBookId(Long bookId);
}
