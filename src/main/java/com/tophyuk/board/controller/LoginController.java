package com.tophyuk.board.controller;

import com.tophyuk.board.domain.User;
import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

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

    /* 이메일 중복 체크 */
    @GetMapping("/checkEmail")
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email, @RequestParam("loginType") String loginType) {
        return userService.checkEmail(email, loginType);
    }

    /* 이메일로 임시 비밀번호 발송 */
    @PostMapping("/sendPassword")
    public String sendPassword(@RequestParam("email") String email, @RequestParam("loginType") String loginType) {

        // 임시 패스워드로 수정 및 이메일 발송
        userService.randomPwUpdateSendEMail(email, loginType);


        return "redirect:/login";
    }
}
