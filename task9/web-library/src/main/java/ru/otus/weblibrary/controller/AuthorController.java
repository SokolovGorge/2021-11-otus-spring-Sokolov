package ru.otus.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.weblibrary.service.AuthorService;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("authorlist")
    public String authorList(Model model) {
        val authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "authorlist";
    }

}
