package com.tophyuk.board.controller;

import com.tophyuk.board.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().toString().indexOf("ROLE_ANONYMOUS") > -1) {
            // 익명
            return "login";
        } else {
            // 로그인한 사용자
            return "redirect:/main";
        }

    }

    @GetMapping("/main")
    public String main(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UserDto userDto = (UserDto) principal;

        model.addAttribute("userDto", userDto);

        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
