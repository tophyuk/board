package com.tophyuk.board.repository;

import com.tophyuk.board.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);
    boolean existsByEmailAndLoginType(String email, String loginType);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndLoginType(String email, String loginType);

    @Transactional
    @Query(value = "UPDATE user SET password = :password WHERE email = :email AND login_type = :loginType", nativeQuery= true)
    @Modifying(clearAutomatically = true)
    void updatePwdByEmailAndType(@Param("password") String password, @Param("email") String email, @Param("loginType") String loginType);


}
