package com.sanedge.ecommerce_midtrans.exceptions;

public class RefreshTokenException extends RuntimeException {

    private final String token;

    public RefreshTokenException(String token, String message) {
        super(message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}