package com.sanedge.ecommerce_midtrans.domain.response.user;

import java.security.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private boolean isStaff;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}