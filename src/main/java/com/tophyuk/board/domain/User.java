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

    @Column(unique = true, length = 10, nullable = false)
    @Size(min = 2, max = 10)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(long id, String nickname, String email, String password, String region, Role role) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.region = region;
        this.role = role;
    }

}
