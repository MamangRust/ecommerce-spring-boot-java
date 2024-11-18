package com.sanedge.ecommerce_midtrans.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sanedge.ecommerce_midtrans.domain.request.auth.LoginRequest;
import com.sanedge.ecommerce_midtrans.domain.request.auth.RegisterRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@Validated @RequestBody LoginRequest loginRequest) {
        log.info("Login request received for email: {}", loginRequest.getEmail());
        MessageResponse response = authService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Validated @RequestBody RegisterRequest registerRequest) {
        log.info("Register request received for email: {}", registerRequest.getEmail());
        MessageResponse response = authService.register(registerRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<MessageResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        log.info("Refresh token request received");
        MessageResponse response = authService.refreshToken(refreshToken);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<MessageResponse> getCurrentUser() {
        log.info("Get current user request received");
        MessageResponse response = authService.getCurrentUser();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
