package com.sanedge.ecommerce_midtrans.domain.request.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequest {
    @NotEmpty(message = "Name is required")
    @JsonProperty("name")
    private String name;

    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email is required")
    @JsonProperty("email")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @JsonProperty("password")
    private String password;

    @NotEmpty(message = "Confirm password is required")
    @JsonProperty("confirm_password")
    private String confirmPassword;

    public boolean isPasswordValid() {
        return password != null && password.equals(confirmPassword);
    }
}
