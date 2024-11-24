package com.sanedge.ecommerce_midtrans.service;

import java.util.Optional;

import com.sanedge.ecommerce_midtrans.models.RefreshToken;
import com.sanedge.ecommerce_midtrans.models.User;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(Long userId, String token);

    RefreshToken verifyExpiration(RefreshToken token);

    Optional<RefreshToken> findByUser(User user);

    int deleteByUserId(Long userId);

    RefreshToken updateExpiryDate(RefreshToken refreshToken);
}
