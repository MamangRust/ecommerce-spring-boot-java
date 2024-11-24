package com.sanedge.ecommerce_midtrans.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sanedge.ecommerce_midtrans.domain.response.user.UserResponse;
import com.sanedge.ecommerce_midtrans.models.User;

@Component
public class UserMapper {
     public  UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId().intValue())
                .name(user.getName())
                .email(user.getEmail())
                .isStaff(user.isStaff())
                .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null)
                .updatedAt(user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null)
                .build();
    }

    public  List<UserResponse> toUserResponses(List<User> users) {
        if (users == null || users.isEmpty()) {
            return List.of();
        }

        return users.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }
}
