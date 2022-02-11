package ru.otus.restlibrary.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookPageController {

    @GetMapping("/")
    public String bookListPage(Model model) {
        return "booklist";
    }

    @GetMapping("/bookedit")
    public String bookEditPage(Model model) {
        return "bookedit";
    }
}
