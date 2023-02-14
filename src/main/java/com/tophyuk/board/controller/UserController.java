package com.tophyuk.board.controller;

import com.tophyuk.board.domain.User;
import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.service.UserService;
import com.tophyuk.board.validation.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    /** 스프링 파라미터 바인딩 역할 + 검증 기능 **/
    @InitBinder // 여기 해당 컨트롤러에서만 영향, 글로벌 설정은 별도로 해야함.
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(userValidator); // WebDataBinder에 userValidator 검증기를 추가하면 여기 컨트롤러에서 검증기를 자동으로 적용 가능
    }

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
        return "signup";
    }

    @PostMapping("signup")
    public String signup(@Validated UserDto userDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "/signup";
        }

        //todo - 성공로직
        try {
            userService.signup(userDto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/signup";
        }

        return "redirect:/login";
    }
}
