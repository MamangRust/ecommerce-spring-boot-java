package com.sanedge.ecommerce_midtrans.domain.request.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshTokenRequest {
    @NotEmpty(message = "Refresh token is required")
    @JsonProperty("refresh_token")
    private String refreshToken;
}
