package com.tophyuk.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "errorMessage", required = false) String errorMessage, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().toString().indexOf("ROLE_ANONYMOUS") > -1) {
            // 익명
            model.addAttribute("error", error);
            model.addAttribute("errorMessage", errorMessage);
            return "login";

        } else {
            // 로그인한 사용자
            return "redirect:/main";
        }
    }

}
