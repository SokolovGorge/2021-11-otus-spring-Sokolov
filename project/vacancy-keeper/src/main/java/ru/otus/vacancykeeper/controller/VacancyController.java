package ru.otus.vacancykeeper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.vacancykeeper.domain.Vacancy;
import ru.otus.vacancykeeper.search.VacancySearchValues;
import ru.otus.vacancykeeper.service.VacancyService;

@RestController
@RequestMapping("/vacancy")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping("/search")
    public ResponseEntity<Page<Vacancy>> search(@RequestBody VacancySearchValues vacancySearchValues) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        PageRequest pageRequest = PageRequest.of(vacancySearchValues.getPageNumber(), vacancySearchValues.getPageSize(), sort);

        Page<Vacancy> page = vacancyService.findVacancyByParameters(vacancySearchValues.getTaskId(), vacancySearchValues.getText(), pageRequest);
        return ResponseEntity.ok(page);
    }

}
