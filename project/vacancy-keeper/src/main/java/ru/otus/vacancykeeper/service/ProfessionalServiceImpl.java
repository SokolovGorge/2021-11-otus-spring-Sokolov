package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vacancykeeper.domain.Professional;
import ru.otus.vacancykeeper.repository.ProfessionalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Professional> findAll() {
        return professionalRepository.findAll();
    }
}
