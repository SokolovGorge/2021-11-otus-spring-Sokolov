package ru.otus.vacancykeeper.explorer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.otus.vacancycommon.dto.VacancyCommon;
import ru.otus.vacancycommon.dto.VacancyPackage;
import ru.otus.vacancykeeper.dto.TaskDto;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class VacancyIterator implements Iterator<List<VacancyCommon>> {

    private final RestTemplate restTemplate;

    private final String service;
    private final TaskDto task;

    private int page = 0;
    private int pages = 0;

    @Override
    public boolean hasNext() {
        return page == 0 || page < pages;
    }

    @Override
    public List<VacancyCommon> next() {
        VacancyPackage vacancyPackage = restTemplate.getForObject(buildRequest(), VacancyPackage.class);
        assert vacancyPackage != null;
        pages = vacancyPackage.getPages();
        page = vacancyPackage.getPage() + 1;
        return vacancyPackage.getItems();
    }

    private String buildRequest() {
        final StringBuilder sb = new StringBuilder("http://")
                .append(service)
                .append("/api/vacancy?profcode=")
                .append(task.getProfCode())
                .append("&areacode=")
                .append(task.getAreaCode());
        if (task.getKeywords() != null && !"".equals(task.getKeywords().trim())) {
            sb.append("&words=")
                    .append(task.getKeywords());
        }
        if (page > 0) {
            sb.append("&page=")
                    .append(page);
        }
        return sb.toString();
    }
}
