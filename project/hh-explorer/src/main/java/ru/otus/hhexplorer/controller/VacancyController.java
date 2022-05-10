package ru.otus.hhexplorer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hhexplorer.dto.common.RequestItem;
import ru.otus.hhexplorer.dto.common.VacancyPackage;
import ru.otus.hhexplorer.exception.ApplicationException;
import ru.otus.hhexplorer.service.VacancyService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping("/vacancy")
    public ResponseEntity<VacancyPackage> getVacancy(@RequestParam(name = "profcode") String profcode,
                                                     @RequestParam(name = "areacode") String areacode,
                                                     @RequestParam(name = "words", required = false) String words,
                                                     @RequestParam(name = "page", required = false) Integer page) {
        try {
            return ResponseEntity.ok(vacancyService.getVacancies(new RequestItem(profcode, areacode, words, page)));
        } catch (ApplicationException ex) {
            return new ResponseEntity(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
