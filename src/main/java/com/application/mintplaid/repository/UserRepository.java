package com.application.mintplaid.repository;

import com.application.mintplaid.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findByEmail(String email);

    Optional<User> findUserByVerificationCode(String verificationCode);
}
