package ru.otus.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.weblibrary.service.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("genrelist")
    public String genreList(Model model) {
        val genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);
        return "genrelist";
    }

}
