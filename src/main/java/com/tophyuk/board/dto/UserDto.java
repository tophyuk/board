package com.tophyuk.board.dto;

import com.tophyuk.board.auth.userinfo.OAuth2UserInfo;
import com.tophyuk.board.domain.Role;
import com.tophyuk.board.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@NoArgsConstructor
public class UserDto implements UserDetails, OAuth2User{
    @NotBlank(message = "사용자 명은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,30}$", message = "사용자명은 특수문자를 제외한 2~30자리여야 합니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password2;

    @NotBlank(message = "지역은 필수 입력 값입니다.")
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    private String picture;

    private String loginType;

    //private Map<String, Object> attributes;
    private OAuth2UserInfo oAuth2UserInfo;

    @Builder
    public UserDto(String nickname, String email, String password, String region, Role role, String picture, OAuth2UserInfo oAuth2UserInfo, String loginType) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.region = region;
        this.role = role;
        this.picture = picture;
        this.loginType = loginType;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public User toEntity() {
        User user = User.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .region(region)
                .role(Role.USER)
                .picture(picture)
                .loginType(loginType)
                .build();

        return user;

    }

    // user 조회시에 Entity -> DAO 변환
    public UserDto toUserDto(User user){

        UserDto userDto = UserDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .region(user.getRegion())
                .role(user.getRole())
                .picture(user.getPicture())
                .loginType(user.getLoginType())
                .build();
        return userDto;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getKey()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2UserInfo.getAttributes();
    }

    @Override
    public String getName() {
       return null;
    }
}
