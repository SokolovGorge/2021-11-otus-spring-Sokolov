package ru.otus.hhexplorer.service;

import org.springframework.stereotype.Component;
import ru.otus.hhexplorer.dto.common.Vacancy;
import ru.otus.hhexplorer.dto.common.VacancyPackage;
import ru.otus.hhexplorer.dto.hh.HHVacancy;
import ru.otus.hhexplorer.dto.hh.HHVacancyPage;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class HHVacancyConverter {

    public VacancyPackage convert(HHVacancyPage vacancyPage) {
        VacancyPackage vacancyPackage = new VacancyPackage();
        vacancyPackage.setPage(vacancyPage.getPage());
        vacancyPackage.setPages(vacancyPage.getPages());
        vacancyPackage.setItems(Arrays.stream(vacancyPage.getItems()).map(this::convert).collect(Collectors.toList()));
        return vacancyPackage;
    }

    private Vacancy convert(HHVacancy hhvacancy) {
        final Vacancy vacancy = new Vacancy();
        vacancy.setId(hhvacancy.getId());
        vacancy.setName(hhvacancy.getName());
        if (hhvacancy.getSalary() != null) {
            vacancy.setSalaryMin(hhvacancy.getSalary().getFrom());
            vacancy.setSalaryMax(hhvacancy.getSalary().getTo());
            vacancy.setCurrency(hhvacancy.getSalary().getCurrency());
        }
        if (hhvacancy.getSchedule() != null) {
            vacancy.setSchedule(hhvacancy.getSchedule().getName());
        }
        if (hhvacancy.getAddress() != null) {
            vacancy.setAddress(hhvacancy.getAddress().getRaw());
            vacancy.setAddressLat(hhvacancy.getAddress().getLat());
            vacancy.setAddressLng(hhvacancy.getAddress().getLng());
        }
        if (hhvacancy.getEmployer() != null) {
            vacancy.setEmployerName(hhvacancy.getEmployer().getName());
            vacancy.setEmployerURL(hhvacancy.getEmployer().getAlternate_url());
        }
        if (hhvacancy.getSnippet() != null) {
            vacancy.setRequirement(clearHtmlTags(hhvacancy.getSnippet().getRequirement()));
            vacancy.setResponsibility(clearHtmlTags(hhvacancy.getSnippet().getResponsibility()));
        }
        vacancy.setSourceURL(hhvacancy.getAlternate_url());
        return vacancy;
    }

    private String clearHtmlTags(String text) {
        if (null == text) {
            return null;
        }
        return text.replaceAll("\\<[^>]*>","");
    }

}
