package com.tophyuk.board.repository;

import com.tophyuk.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);
    boolean existsByEmailAndLoginType(String email, String loginType);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndLoginType(String email, String loginType);
}
