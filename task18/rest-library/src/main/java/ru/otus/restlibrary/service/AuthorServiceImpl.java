package ru.otus.restlibrary.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.restlibrary.dto.AuthorDto;
import ru.otus.restlibrary.repository.AuthorRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @HystrixCommand(commandKey = "getAuthorKey", fallbackMethod = "getDummyList")
    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream().map(AuthorDto::new).collect(Collectors.toList());
    }

    public List<AuthorDto> getDummyList() {
        return Collections.singletonList(AuthorDto.DUMMY);
    }
}
