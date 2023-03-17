package com.tophyuk.board.service;

import com.tophyuk.board.domain.User;
import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    public final UserRepository userRepository;
    public final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JavaMailSender javaMailSender;
    private static final String subject = "임시 비밀번호 안내 이메일입니다.";
    private static  String text = "안녕하세요. tophyuk의 임시 비밀번호 안내 메일입니다. "
            +"\n" + "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요."+"\n";
    private static final String fromAddress = "shjung8278@gmail.com";


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

    public boolean checkEmail(String email, String loginType) {
        boolean existsByEmailAndLoginType = userRepository.existsByEmailAndLoginType(email, loginType);
        return existsByEmailAndLoginType;
    }

    public void randomPwUpdateSendEMail(String email, String loginType) {

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = uuid + "Q!";

        String EncoderPassword = bCryptPasswordEncoder.encode(password);
        userRepository.updatePwdByEmailAndType(EncoderPassword, email, loginType);

        //SMTP 서버로 이메일 보내기
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(fromAddress);
            helper.setSubject(subject);
            text = text.concat(password);
            helper.setText(text);
            helper.setTo(email);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 일반 로그인일 때는 무조건 loginType이 basic으로 고정이기 떄문에 찾을때도 basic으로 찾기
        User user = userRepository.findByEmailAndLoginType(email, "basic").orElseThrow(() -> new UsernameNotFoundException(email + "는 존재하지 않는 이메일입니다."));

        UserDto userDto = new UserDto().toUserDto(user); // User<Entity> -> UserDto 변환.
        return userDto;
    }
}
