package com.sanedge.ecommerce_midtrans.service;

import com.sanedge.ecommerce_midtrans.domain.request.auth.LoginRequest;
import com.sanedge.ecommerce_midtrans.domain.request.auth.RegisterRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;

public interface AuthService {
    public MessageResponse login(LoginRequest loginRequest);
    public MessageResponse register(RegisterRequest registerRequest);
    public MessageResponse refreshToken(String refreshToken);
    public MessageResponse getCurrentUser();
}
