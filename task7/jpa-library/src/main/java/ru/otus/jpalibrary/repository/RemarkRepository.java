package ru.otus.jpalibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.jpalibrary.domain.Remark;

import java.util.List;

public interface RemarkRepository extends JpaRepository<Remark, Long> {

    List<Remark> findAllRemarksByBookId(Long bookId);
}
