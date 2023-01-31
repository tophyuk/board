package com.tophyuk.board.dto;

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

@Data
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "사용자 명은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "사용자명은 특수문자를 제외한 2~10자리여야 합니다.")
    private String username;

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

    @Builder
    public UserDto(String username, String email, String password, String region, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.region = region;
        this.role = role;
    }

    public User toEntity() {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .region(region)
                .role(Role.USER)
                .build();

        return user;

    }

    // 추후에 사용 예정
    // user 조회시에 enttity -> DAO 변환
    public UserDto toUserDto(User user){

        UserDto userDto = UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .region(user.getRegion())
                .role(user.getRole())
                .build();
        return userDto;
    }

}
