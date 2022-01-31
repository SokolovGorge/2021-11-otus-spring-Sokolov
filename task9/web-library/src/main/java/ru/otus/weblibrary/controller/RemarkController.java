package ru.otus.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.weblibrary.model.RemarkEditModel;
import ru.otus.weblibrary.service.RemarkService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RemarkController {

    private final RemarkService remarkService;

    @GetMapping("remarklist")
    public String remarkList(@RequestParam("bookId") long bookId, Model model) {
        val remarks = remarkService.getAllRemarksByBook(bookId);
        model.addAttribute("remarks", remarks);
        model.addAttribute("bookId", bookId);
        return "remarklist";
    }

    @GetMapping("remarknew")
    public String newRemark(@RequestParam("bookId") long bookId, Model model) {
        val remarkEditModel = new RemarkEditModel();
        remarkEditModel.setBookId(bookId);
        model.addAttribute("remark", remarkEditModel);
        return "remarknew";
    }

    @PostMapping("/remarknew")
    public String saveRemark(@Valid @ModelAttribute("remark") RemarkEditModel remark, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "remarknew";
        }
        remarkService.addRemark(remark.getBookId(), remark.getText());
        return "redirect:/remarklist?bookId=" + remark.getBookId();
    }



}
