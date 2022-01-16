package ru.otus.ormlibrary.repositories;

import ru.otus.ormlibrary.models.Remark;

import java.util.List;
import java.util.Optional;

public interface RemarkRepository {

    List<Remark> findAllByBook(long bookId);

    Remark save(Remark remark);

    void deleteById(long id);

    Optional<Remark> findById(long id);
}
