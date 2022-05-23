package ru.otus.vacancykeeper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.vacancykeeper.domain.Area;
import ru.otus.vacancykeeper.service.AreaService;

import java.util.List;

@RestController
@RequestMapping("/area")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @GetMapping("/all")
    public List<Area> findAll() {
        return areaService.findAll();
    }
}
