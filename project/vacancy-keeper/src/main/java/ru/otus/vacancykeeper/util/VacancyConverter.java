package ru.otus.vacancykeeper.util;

import ru.otus.vacancykeeper.domain.Vacancy;
import ru.otus.vacancykeeper.dto.VacancyDto;

public class VacancyConverter {

    public static Vacancy dtoToEntity(String scode, VacancyDto vacancyDto) {
        final Vacancy vacancy = new Vacancy();
        vacancy.setScode(scode);
        vacancy.setSid(vacancyDto.getId());
        vacancy.setName(vacancyDto.getName());
        vacancy.setSalaryMin(vacancyDto.getSalaryMin());
        vacancy.setSalaryMax(vacancyDto.getSalaryMax());
        vacancy.setCurrency(vacancyDto.getCurrency());
        vacancy.setSchedule(vacancyDto.getSchedule());
        vacancy.setAddress(vacancyDto.getAddress());
        vacancy.setAddrLat(vacancyDto.getAddressLat());
        vacancy.setAddrLng(vacancyDto.getAddressLng());
        vacancy.setEmployerName(vacancyDto.getEmployerName());
        vacancy.setEmployerUrl(vacancyDto.getEmployerURL());
        vacancy.setRequirement(vacancyDto.getRequirement());
        vacancy.setResponsibility(vacancyDto.getResponsibility());
        vacancy.setSourceUrl(vacancyDto.getSourceURL());
        return vacancy;
    }

    private VacancyConverter() {
    }
}
