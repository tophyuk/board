package com.tophyuk.board.repository;

import com.tophyuk.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
