package ru.otus.weblibrary.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.weblibrary.domain.Remark;
import ru.otus.weblibrary.dto.RemarkDto;
import ru.otus.weblibrary.exceptions.ApplicationException;
import ru.otus.weblibrary.repository.BookRepository;
import ru.otus.weblibrary.repository.RemarkRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RemarkServiceImpl implements RemarkService {

    private final BookRepository bookRepository;
    private final RemarkRepository remarkRepository;

    @Transactional(readOnly = true)
    @Override
    public List<RemarkDto> getAllRemarksByBook(long bookId) {
        return remarkRepository.findAllRemarksByBookId(bookId).stream().map(RemarkDto::new).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RemarkDto addRemark(long bookId, String text) {
        val book = bookRepository.findById(bookId).orElseThrow(() -> new ApplicationException("Не найдена книга Id = " + bookId));
        val remark = new Remark(null, book, text);
        return new RemarkDto(remarkRepository.save(remark));
    }
}
