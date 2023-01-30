package com.tophyuk.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@Slf4j
public class UserController {

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
    public String goSignup(Model model) {

        model.addAttribute("regions", regions());
        return "signup";
    }

    @PostMapping("signup")
    public String signup() {

        return "redirect:/";
    }
}
