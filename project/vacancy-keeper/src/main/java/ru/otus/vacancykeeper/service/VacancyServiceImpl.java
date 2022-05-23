package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vacancykeeper.domain.Vacancy;
import ru.otus.vacancykeeper.repository.VacancyRepository;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

    @Transactional(readOnly = true)
    @Override
    public Vacancy findById(Long id) {
        return vacancyRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public Vacancy save(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        vacancyRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Vacancy> findVacancyByParameters(Long taskId, String name, Pageable pageable) {
        return vacancyRepository.findVacancyByParameters(taskId, name, pageable);
    }
}
