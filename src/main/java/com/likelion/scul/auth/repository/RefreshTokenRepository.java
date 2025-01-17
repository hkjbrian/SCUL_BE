package com.likelion.scul.auth.repository;

import com.likelion.scul.auth.domain.RefreshToken;
import com.likelion.scul.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
