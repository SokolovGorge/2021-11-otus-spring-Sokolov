package ru.otus.weblibrary.service;

import ru.otus.weblibrary.dto.RemarkDto;

import java.util.List;

public interface RemarkService {

    List<RemarkDto> getAllRemarksByBook(long bookId);

    RemarkDto addRemark(long bookId, String text);
}
