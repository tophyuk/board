package com.tophyuk.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class User extends Time{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 30, nullable = false)
    @Size(min = 2, max = 30)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String loginType;


    @Builder
    public User(long id, String nickname, String email, String picture, String password, String region, Role role, String loginType) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.password = password;
        this.region = region;
        this.role = role;
        this.loginType = loginType;
    }

}
