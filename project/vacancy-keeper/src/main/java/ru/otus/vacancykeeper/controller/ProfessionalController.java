package ru.otus.vacancykeeper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.vacancykeeper.domain.Professional;
import ru.otus.vacancykeeper.service.ProfessionalService;

import java.util.List;

@RestController
@RequestMapping("/professional")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @GetMapping("/all")
    public List<Professional> findAll() {
        return professionalService.findAll();
    }

}
