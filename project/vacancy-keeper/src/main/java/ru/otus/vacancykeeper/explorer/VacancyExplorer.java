package ru.otus.vacancykeeper.explorer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.otus.vacancycommon.dto.VacancyCommon;
import ru.otus.vacancykeeper.dto.TaskDto;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class VacancyExplorer implements Iterable<List<VacancyCommon>> {

    private final RestTemplate restTemplate;
    private final String sourceCode;
    private final String service;
    private final TaskDto task;

    @Override
    public Iterator<List<VacancyCommon>> iterator() {
        return new VacancyIterator(restTemplate, service, task);
    }
}
