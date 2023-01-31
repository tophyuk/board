package com.tophyuk.board.service;

import com.tophyuk.board.domain.User;
import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;

    public final PasswordEncoder passwordEncoder;


    public void signup(UserDto userDto) {

        //패스워드 암호화 후 적용
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        log.info("password = {}", userDto.getPassword());

        userRepository.save(userDto.toEntity());
    }

    public boolean checkUsername(String username){
        boolean existsByUsername = userRepository.existsByUsername(username);
        return existsByUsername;
    }

    public boolean checkEmail(String email) {
        boolean existsByEmail = userRepository.existsByEmail(email);
        return existsByEmail;
    }



}
