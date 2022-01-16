package ru.otus.ormlibrary.service;

import ru.otus.ormlibrary.dto.RemarkDto;

import java.util.List;

public interface RemarkService {

    List<RemarkDto> getAllRemarksByBook(long bookId);

    RemarkDto addRemark(long bookId, String text);
}
