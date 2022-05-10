package ru.otus.hhexplorer.service;

import ru.otus.hhexplorer.dto.common.RequestItem;
import ru.otus.hhexplorer.dto.common.VacancyPackage;
import ru.otus.hhexplorer.exception.ApplicationException;

public interface VacancyService {

    VacancyPackage getVacancies(RequestItem requestItem) throws ApplicationException;
}
