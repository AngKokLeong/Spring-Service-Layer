package com.workshop.bookshelf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.workshop.bookshelf.model.Book;
import com.workshop.bookshelf.service.LoanService;

@Controller
public class LoanController {
    

    private final LoanService loanService;

    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }


    @GetMapping("/loan/bypass-proxy")
    public String showBypassProxy(Model model){
        return "bypass-proxy";
    }

    @PostMapping("/loan/bypass-proxy")
    public String showBypassProxy(@ModelAttribute Book book, Model model){

        loanService.outer();

        return "redirect:/bypass-proxy";
    }
}
