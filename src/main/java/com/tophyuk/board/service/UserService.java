package com.tophyuk.board.service;

import com.tophyuk.board.domain.User;
import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    public final UserRepository userRepository;
    public final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void signup(UserDto userDto) {

        //패스워드 암호화 후 적용
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        log.info("password = {}", userDto.getPassword());

        userRepository.save(userDto.toEntity());
    }

    public boolean checkNickname(String nickname){
        boolean existsByUsername = userRepository.existsByNickname(nickname);
        return existsByUsername;
    }

    public boolean checkEmail(String email) {
        boolean existsByEmail = userRepository.existsByEmail(email);
        return existsByEmail;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "는 존재하지 않는 이메일입니다."));

        UserDto userDto = new UserDto().toUserDto(user); // User<Entity> -> UserDto 변환.
        return userDto;
    }
}
