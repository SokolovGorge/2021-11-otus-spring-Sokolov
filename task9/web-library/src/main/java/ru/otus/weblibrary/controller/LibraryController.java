package ru.otus.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LibraryController {

    @GetMapping("/")
    public String showMain() {
        return "main";
    }

}
