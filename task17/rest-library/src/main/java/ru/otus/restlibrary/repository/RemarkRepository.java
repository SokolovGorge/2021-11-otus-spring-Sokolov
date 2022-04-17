package ru.otus.restlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.restlibrary.domain.Remark;

import java.util.List;

public interface RemarkRepository extends JpaRepository<Remark, Long> {

    List<Remark> findAllRemarksByBookId(Long bookId);
}
