package com.sanedge.ecommerce_midtrans.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sanedge.ecommerce_midtrans.domain.request.carts.CreateRequestCart;
import com.sanedge.ecommerce_midtrans.domain.request.carts.DeleteRequestCarts;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.service.CartService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MessageResponse> getCartsByUserId(@PathVariable int userId) {
        log.info("Fetching carts for user ID: {}", userId);
        MessageResponse response = cartService.findAllUserByID(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createCart(@Validated @RequestBody CreateRequestCart request) {
        log.info("Creating a cart for user ID: {}", request.getUserId());
        MessageResponse response = cartService.create(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<MessageResponse> deleteCart(@PathVariable int cartId) {
        log.info("Deleting cart with ID: {}", cartId);
        MessageResponse response = cartService.delete(cartId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/delete-many")
    public ResponseEntity<MessageResponse> deleteMultipleCarts(@Validated @RequestBody DeleteRequestCarts request) {
        log.info("Deleting multiple carts with IDs: {}", request.getCartIds());
        MessageResponse response = cartService.deleteMany(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}

