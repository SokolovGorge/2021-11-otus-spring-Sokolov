package ru.otus.hhexplorer.service;

import ru.otus.hhexplorer.dto.common.RequestItem;
import ru.otus.hhexplorer.exception.ApplicationException;
import ru.otus.vacancycommon.dto.VacancyPackage;

public interface VacancyService {

    VacancyPackage getVacancies(RequestItem requestItem) throws ApplicationException;
}
