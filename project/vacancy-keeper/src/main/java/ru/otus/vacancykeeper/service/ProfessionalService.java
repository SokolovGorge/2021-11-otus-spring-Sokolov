package ru.otus.vacancykeeper.service;

import ru.otus.vacancykeeper.domain.Professional;

import java.util.List;

public interface ProfessionalService {

    List<Professional> findAll();
}
