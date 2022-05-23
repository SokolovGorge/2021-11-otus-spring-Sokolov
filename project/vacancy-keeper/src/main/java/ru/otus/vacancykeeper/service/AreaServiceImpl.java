package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vacancykeeper.domain.Area;
import ru.otus.vacancykeeper.repository.AreaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Area> findAll() {
        return areaRepository.findAll();
    }
}
