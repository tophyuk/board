package com.tophyuk.board.validation;

import com.tophyuk.board.domain.User;
import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.repository.UserRepository;
import com.tophyuk.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor

public class UserValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;

        // 비밀번호, 비밀번호 확인이 같은지 확인
        if (!userDto.getPassword().equals(userDto.getPassword2())) {
            errors.rejectValue("password", "passwordUnmatched",
                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 사용자명 중복 체크
        if (userService.checkNickname(userDto.toEntity().getNickname())) {
            errors.rejectValue("nickname", "nicknameDuplication", "이미 사용중인 닉네임 입니다.");
        }

        // 이메일 중복 체크
        if (userService.checkEmail(userDto.toEntity().getEmail())) {
            errors.rejectValue("email", "emailDuplication", "이미 사용중인 이메일 입니다.");
        }

    }
}
