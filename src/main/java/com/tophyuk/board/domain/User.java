package com.tophyuk.board.domain;

import jakarta.persistence.*;
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

    @Column(length = 8, nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String region;

    @Builder
    public User(long id, String userName, String email, String password, String region) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.region = region;
    }
}
