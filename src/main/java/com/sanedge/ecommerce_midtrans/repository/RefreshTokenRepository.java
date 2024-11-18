package com.sanedge.ecommerce_midtrans.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.sanedge.ecommerce_midtrans.models.RefreshToken;
import com.sanedge.ecommerce_midtrans.models.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    @Modifying
    int deleteByUser(User user);
}