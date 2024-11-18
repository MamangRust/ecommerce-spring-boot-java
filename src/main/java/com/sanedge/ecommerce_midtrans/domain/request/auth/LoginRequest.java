package com.sanedge.ecommerce_midtrans.domain.request.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest {
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email is required")
    @JsonProperty("email")
    private String email;

    @NotEmpty(message = "Password is required")
    @JsonProperty("password")
    private String password;
}
