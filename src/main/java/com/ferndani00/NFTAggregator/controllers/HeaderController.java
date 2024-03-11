package com.ferndani00.NFTAggregator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HeaderController {

    @GetMapping("/account")
    public String showAccount(Model model) {
        return "account";
    }
}
