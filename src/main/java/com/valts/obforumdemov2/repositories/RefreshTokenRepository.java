package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.models.RefreshToken;
import com.valts.obforumdemov2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Override
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);
    @Modifying
    int deleteByUser(User user);
}
