package com.tophyuk.board.service;

import com.tophyuk.board.domain.User;
import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;

    public void signup(UserDto userDto) {
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
