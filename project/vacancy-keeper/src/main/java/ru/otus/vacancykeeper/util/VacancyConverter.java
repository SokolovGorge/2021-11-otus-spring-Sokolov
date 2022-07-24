package ru.otus.vacancykeeper.util;

import ru.otus.vacancycommon.dto.VacancyCommon;
import ru.otus.vacancykeeper.domain.Vacancy;

public class VacancyConverter {

    public static Vacancy dtoToEntity(String scode, VacancyCommon vacancyCommon) {
        final Vacancy vacancy = new Vacancy();
        vacancy.setScode(scode);
        vacancy.setSid(vacancyCommon.getId());
        vacancy.setName(vacancyCommon.getName());
        vacancy.setSalaryMin(vacancyCommon.getSalaryMin());
        vacancy.setSalaryMax(vacancyCommon.getSalaryMax());
        vacancy.setCurrency(vacancyCommon.getCurrency());
        vacancy.setSchedule(vacancyCommon.getSchedule());
        vacancy.setAddress(vacancyCommon.getAddress());
        vacancy.setAddrLat(vacancyCommon.getAddressLat());
        vacancy.setAddrLng(vacancyCommon.getAddressLng());
        vacancy.setEmployerName(vacancyCommon.getEmployerName());
        vacancy.setEmployerUrl(vacancyCommon.getEmployerURL());
        vacancy.setRequirement(vacancyCommon.getRequirement());
        vacancy.setResponsibility(vacancyCommon.getResponsibility());
        vacancy.setSourceUrl(vacancyCommon.getSourceURL());
        return vacancy;
    }

    private VacancyConverter() {
    }
}
