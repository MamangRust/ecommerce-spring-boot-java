package com.sanedge.ecommerce_midtrans.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanedge.ecommerce_midtrans.domain.request.auth.RegisterRequest;
import com.sanedge.ecommerce_midtrans.domain.request.user.UpdateUserRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.domain.response.user.UserResponse;
import com.sanedge.ecommerce_midtrans.models.User;
import com.sanedge.ecommerce_midtrans.repository.UserRepository;
import com.sanedge.ecommerce_midtrans.service.UserService;

@Service
public class UserImplService implements UserService {

    private final UserRepository userRepository;
      private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserImplService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MessageResponse getUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<UserResponse> userResponses = users.stream()
                    .map(user -> UserResponse.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .isStaff(user.isStaff())
                            .createdAt(user.getCreatedAt())
                            .updatedAt(user.getUpdatedAt())
                            .build())
                    .collect(Collectors.toList());

            return MessageResponse.builder()
                    .message("Users retrieved successfully")
                    .data(userResponses)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Failed to retrieve users")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse createUser(RegisterRequest request) {
        try {
            User newUser = new User();

            newUser.setName(request.getName());
            newUser.setStaff(false);
            newUser.setEmail(request.getEmail());
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));

            User savedUser = userRepository.save(newUser);
            UserResponse userResponse = UserResponse.builder()
                    .id(savedUser.getId())
                    .name(savedUser.getName())
                    .email(savedUser.getEmail())
                    .isStaff(savedUser.isStaff())
                    .createdAt(savedUser.getCreatedAt()) 
                    .updatedAt(savedUser.getUpdatedAt())
                    .build();

            return MessageResponse.builder()
                    .message("User created successfully")
                    .data(userResponse)
                    .statusCode(201)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Failed to create user")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse getUser(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            UserResponse userResponse = UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .isStaff(user.isStaff()) // Assuming this field exists
                    .createdAt(user.getCreatedAt()) // Ensure the createdAt field is being saved
                    .updatedAt(user.getUpdatedAt()) // Ensure the updatedAt field is being saved
                    .build();

            return MessageResponse.<UserResponse>builder()
                    .message("User retrieved successfully")
                    .data(userResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return MessageResponse.<UserResponse>builder()
                    .message("Failed to retrieve user")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse updateUser(UpdateUserRequest request) {
        try {
            User user = userRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());

            User updatedUser = userRepository.save(user);
            UserResponse userResponse = UserResponse.builder()
                    .id(updatedUser.getId())
                    .name(updatedUser.getName())
                    .email(updatedUser.getEmail())
                    .isStaff(updatedUser.isStaff()) // Assuming this field exists
                    .createdAt(updatedUser.getCreatedAt()) // Ensure the createdAt field is being saved
                    .updatedAt(updatedUser.getUpdatedAt()) // Ensure the updatedAt field is being saved
                    .build();

            return MessageResponse.builder()
                    .message("User updated successfully")
                    .data(userResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Failed to update user")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse deleteUser(int id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            userRepository.delete(user);

            return MessageResponse.builder()
                    .message("User deleted successfully")
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Failed to delete user")
                    .statusCode(500)
                    .build();
        }
    }
}
