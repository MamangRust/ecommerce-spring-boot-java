package com.sanedge.ecommerce_midtrans.security;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtProvider {
    static final String ISSUER = "MyApp";

    @Value("${springjwt.app.jwtSecret}")
    private String jwtSecret;

    @Value("${springjwt.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${springjwt.app.jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    private final Algorithm accessTokenAlgorithm;
    private final JWTVerifier jwtTokenVerifier;

    public JwtProvider(@Value("${springjwt.app.jwtSecret}") String jwtSecret,
                       @Value("${springjwt.app.jwtExpirationMs}") int jwtExpirationMs,
                       @Value("${springjwt.app.jwtRefreshExpirationMs}") int jwtRefreshExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtRefreshExpirationMs = jwtRefreshExpirationMs;
        this.accessTokenAlgorithm = Algorithm.HMAC512(jwtSecret);
        this.jwtTokenVerifier = JWT.require(accessTokenAlgorithm)
                .withIssuer(ISSUER)
                .build();
    }

    // Generate access token
    public String generateAccessToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return generateToken(userPrincipal.getId(), userPrincipal.getUsername(), jwtExpirationMs);
    }

    // Generate refresh token
    public String generateRefreshToken(Long id, String name) {
        return generateToken(id, name, jwtRefreshExpirationMs);
    }

  
    public String generateToken(Long id, String name, int expirationMs) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("id", id)
                .withClaim("name", name)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + expirationMs))
                .sign(accessTokenAlgorithm);
    }

    private Optional<DecodedJWT> decodeAccessToken(String token) {
        try {
            return Optional.of(jwtTokenVerifier.verify(token));
        } catch (JWTVerificationException e) {
            log.error("Invalid access token", e);
        }
        return Optional.empty();
    }

    public boolean validateAccessToken(String token) {
        return decodeAccessToken(token).isPresent();
    }

    public Long getIdFromAccessToken(String token) {
        return decodeAccessToken(token)
                .map(decodedJWT -> decodedJWT.getClaim("id").asLong())
                .orElse(null);
    }

    public String getNameFromAccessToken(String token) {
        return decodeAccessToken(token)
                .map(decodedJWT -> decodedJWT.getClaim("name").asString())
                .orElse(null);
    }

    public int getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public int getJwtRefreshExpirationMs() {
        return jwtRefreshExpirationMs;
    }
}
