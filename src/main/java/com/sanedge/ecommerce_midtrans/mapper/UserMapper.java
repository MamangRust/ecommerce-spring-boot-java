package com.sanedge.ecommerce_midtrans.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.sanedge.ecommerce_midtrans.domain.response.user.UserResponse;
import com.sanedge.ecommerce_midtrans.models.User;

public class UserMapper {
     public static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId().intValue())
                .name(user.getName())
                .email(user.getEmail())
                .isStaff(user.isStaff())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static List<UserResponse> toUserResponses(List<User> users) {
        if (users == null || users.isEmpty()) {
            return List.of();
        }

        return users.stream()
                .map(UserMapper::toUserResponse)
                .collect(Collectors.toList());
    }
}
