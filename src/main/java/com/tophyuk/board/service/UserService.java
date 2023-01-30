package com.tophyuk.board.service;

import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;

    public void signup(UserDto userDto) {
        userRepository.save(userDto.toEntity());
    }



}
