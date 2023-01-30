package com.tophyuk.board.controller;

import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("GYEONGGI", "경기");
        regions.put("INCHEON", "인천");
        regions.put("BUSAN", "부산");
        regions.put("DAEGU", "대구");
        regions.put("DAEJEON", "대전");
        regions.put("GWANGJU", "광주");
        regions.put("JEJU", "광주");
        return regions;
    }

    @GetMapping("/signup")
    public String goSignup(UserDto userDto, Model model) {

        model.addAttribute("userDto", userDto);
        model.addAttribute("regions", regions());
        return "signup";
    }

    @PostMapping("signup")
    public String signup(@Valid UserDto userDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "/signup";
        }

        //todo - 성공로직
        userService.signup(userDto);

        return "redirect:/login";
    }
}
