package com.tophyuk.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "errorMessage", required = false) String errorMessage, Model model) {

        model.addAttribute("error", error);
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

}
