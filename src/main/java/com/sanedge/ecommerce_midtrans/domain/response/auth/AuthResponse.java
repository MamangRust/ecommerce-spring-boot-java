package com.sanedge.ecommerce_midtrans.domain.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String access_token;
    private String refresh_token;
    private String expiresAt;
    private String username;
}