package com.sanedge.ecommerce_midtrans.domain.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserRequest {
    @JsonProperty("id") 
    @NotNull(message = "ID is required")
    private int id;

    @JsonProperty("name") 
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;

    @JsonProperty("email") 
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email should not exceed 100 characters")
    private String email;

    @JsonProperty("password")  
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;
}
