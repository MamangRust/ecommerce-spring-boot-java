package com.sanedge.ecommerce_midtrans.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanedge.ecommerce_midtrans.domain.request.auth.LoginRequest;
import com.sanedge.ecommerce_midtrans.domain.request.auth.RegisterRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.domain.response.auth.AuthResponse;
import com.sanedge.ecommerce_midtrans.domain.response.auth.TokenRefreshResponse;
import com.sanedge.ecommerce_midtrans.domain.response.user.UserResponse;
import com.sanedge.ecommerce_midtrans.enums.ERole;
import com.sanedge.ecommerce_midtrans.exceptions.RefreshTokenException;
import com.sanedge.ecommerce_midtrans.exceptions.ResourceNotFoundException;
import com.sanedge.ecommerce_midtrans.mapper.UserMapper;
import com.sanedge.ecommerce_midtrans.models.RefreshToken;
import com.sanedge.ecommerce_midtrans.models.Role;
import com.sanedge.ecommerce_midtrans.models.User;
import com.sanedge.ecommerce_midtrans.repository.RoleRepository;
import com.sanedge.ecommerce_midtrans.repository.UserRepository;
import com.sanedge.ecommerce_midtrans.security.JwtProvider;
import com.sanedge.ecommerce_midtrans.security.UserDetailsImpl;
import com.sanedge.ecommerce_midtrans.service.AuthService;
import com.sanedge.ecommerce_midtrans.service.RefreshTokenService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthImplService implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthImplService(AuthenticationManager authenticationManager, UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
            RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public MessageResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateAccessToken(authentication);
            long expiresAt = jwtProvider.getjwtExpirationMs();
            Date date = new Date();
            date.setTime(expiresAt);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            User user = userRepository.findByEmail(userDetails.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            Optional<RefreshToken> existingRefreshToken = refreshTokenService.findByUser(user);

            if (existingRefreshToken.isPresent()) {
                refreshTokenService.updateExpiryDate(existingRefreshToken.get());
            } else {
                refreshTokenService.deleteByUserId(userDetails.getId());
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
                existingRefreshToken = Optional.of(refreshToken);
            }

            AuthResponse authResponse = AuthResponse.builder()
                    .access_token(jwt)
                    .refresh_token(existingRefreshToken.get().getToken())
                    .expiresAt(dateFormat.format(date))
                    .username(userDetails.getUsername())
                    .build();

            log.info("User successfully logged in: {}", userDetails.getUsername());

            return MessageResponse.builder().message("Berhasil login").data(authResponse).build();

        } catch (AuthenticationException ex) {

            log.error("Authentication failed: {}", ex.getMessage());
            throw new RuntimeException("Invalid email or password");
        } catch (ResourceNotFoundException ex) {
            log.error("User not found: {}", ex.getMessage());
            throw new RuntimeException("User not found");
        } catch (Exception ex) {
            log.error("An unexpected error occurred during login: {}", ex.getMessage());
            throw new RuntimeException("An error occurred while logging in. Please try again later.");
        }
    }

    @Override
    public MessageResponse register(RegisterRequest registerRequest) {
        try {
            User user = new User();
            user.setName(registerRequest.getName());
            user.setStaff(false);
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Error: Default role not found."));
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);

            user.setRoles(roles);

            this.userRepository.save(user);

            UserResponse response = UserMapper.toUserResponse(user);

            log.info("User registered successfully: {}", user.getName());

            return MessageResponse.builder()
                    .message("Successfully created user")
                    .data(response)
                    .statusCode(200)
                    .build();
        } catch (ResourceNotFoundException e) {
            log.error("Role not found: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to create user: Role not found")
                    .data(null)
                    .statusCode(404)
                    .build();
        } catch (Exception e) {
            log.error("Unexpected error during registration: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("An unexpected error occurred while creating user")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse refreshToken(String refreshToken) {
        try {
            return refreshTokenService.findByToken(refreshToken)
                    .map((RefreshToken token) -> refreshTokenService.verifyExpiration(token))
                    .map((RefreshToken verifiedToken) -> verifiedToken.getUser())
                    .map((User user) -> {
                        String newAccessToken = jwtProvider.generateTokenFromUsername(user.getEmail());

                        TokenRefreshResponse tokenResponse = new TokenRefreshResponse(newAccessToken, refreshToken);

                        log.info("Refresh token successful for user: {}", user.getEmail());

                        return MessageResponse.builder()
                                .message("Token refreshed successfully")
                                .data(tokenResponse)
                                .statusCode(200)
                                .build();
                    })
                    .orElseThrow(() -> new RefreshTokenException(refreshToken, "Invalid or expired refresh token"));
        } catch (RefreshTokenException e) {
            log.error("Refresh token error: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to refresh token: " + e.getMessage())
                    .data(null)
                    .statusCode(401)
                    .build();
        } catch (Exception e) {
            log.error("Unexpected error during token refresh: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("An unexpected error occurred while refreshing token")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            User user = this.userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Email not found - " + authentication.getName()));

            UserResponse response = UserMapper.toUserResponse(user);

            log.info("Current user retrieved successfully: {}", user.getEmail());

            return MessageResponse.builder()
                    .message("Retrieved current user successfully")
                    .data(response)
                    .statusCode(200)
                    .build();
        } catch (UsernameNotFoundException e) {
            log.error("User not found: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to retrieve user: " + e.getMessage())
                    .data(null)
                    .statusCode(404)
                    .build();
        } catch (Exception e) {
            log.error("Unexpected error while fetching current user: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("An unexpected error occurred while fetching current user")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

}
