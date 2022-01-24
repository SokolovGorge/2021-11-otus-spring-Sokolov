package ru.otus.mongolibrary.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.mongolibrary.domain.Remark;
import ru.otus.mongolibrary.dto.RemarkDto;
import ru.otus.mongolibrary.exceptions.ApplicationException;
import ru.otus.mongolibrary.repository.BookRepository;
import ru.otus.mongolibrary.repository.RemarkRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RemarkServiceImpl implements RemarkService {

    private final BookRepository bookRepository;
    private final RemarkRepository remarkRepository;

    @Override
    public List<RemarkDto> getAllRemarksByBook(String bookId) {
        val book = bookRepository.findById(bookId).orElseThrow(() -> new ApplicationException("Не найдена книга Id = " + bookId));
        return remarkRepository.findAllRemarksByBook(book).stream().map(RemarkDto::new).collect(Collectors.toList());
    }

    @Override
    public RemarkDto addRemark(String bookId, String text) {
        val book = bookRepository.findById(bookId).orElseThrow(() -> new ApplicationException("Не найдена книга Id = " + bookId));
        val remark = new Remark(book, text);
        return new RemarkDto(remarkRepository.save(remark));
    }
}
