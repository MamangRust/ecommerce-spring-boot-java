package com.sanedge.ecommerce_midtrans.controllers;

import com.sanedge.ecommerce_midtrans.domain.request.auth.RegisterRequest;
import com.sanedge.ecommerce_midtrans.domain.request.user.UpdateUserRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<MessageResponse> getUsers() {
        log.info("Request to get all users");
        MessageResponse response = userService.getUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getUser(@PathVariable int id) {
        log.info("Request to get user with ID: {}", id);
        MessageResponse response = userService.getUser(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createUser(@RequestBody RegisterRequest registerRequest) {
        log.info("Request to create user with data: {}", registerRequest);
        MessageResponse response = userService.createUser(registerRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateUser(@PathVariable int id,
            @RequestBody UpdateUserRequest updateUserRequest) {
        log.info("Request to update user with ID: {} and data: {}", id, updateUserRequest);
        updateUserRequest.setId(id);
        MessageResponse response = userService.updateUser(updateUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable int id) {
        log.info("Request to delete user with ID: {}", id);
        MessageResponse response = userService.deleteUser(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
