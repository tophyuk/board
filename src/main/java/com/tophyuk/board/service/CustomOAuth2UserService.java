package com.tophyuk.board.service;

import com.tophyuk.board.auth.userinfo.GoogleUserInfo;
import com.tophyuk.board.auth.userinfo.KakaoUserInfo;
import com.tophyuk.board.auth.userinfo.NaverUserInfo;
import com.tophyuk.board.auth.userinfo.OAuth2UserInfo;
import com.tophyuk.board.domain.Role;
import com.tophyuk.board.domain.User;
import com.tophyuk.board.dto.UserDto;
import com.tophyuk.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    public final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId(); // google or naver

        if(provider.equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(provider.equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else if(provider.equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        }


        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String nickname = provider+"_"+uuid;  			// 사용자가 입력한 적은 없지만 만들어준다

        String email = oAuth2UserInfo.getEmail();


        String password = bCryptPasswordEncoder.encode("basic1!"+uuid);  // 사용자가 입력한 적은 없지만 만들어준다

        String region = "SEOUL";
        Role role = Role.USER;
        String picture = oAuth2UserInfo.getPicture();
        String loginType = oAuth2UserInfo.getProvider();

        UserDto userDto = new UserDto(nickname, email, password, region, role, picture, oAuth2UserInfo, loginType);
        User user = userDto.toEntity();
        
        User findUser = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(user));

        return userDto;
    }

}
