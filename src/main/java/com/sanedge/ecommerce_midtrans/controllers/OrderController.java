package com.sanedge.ecommerce_midtrans.controllers;

import com.sanedge.ecommerce_midtrans.domain.request.order.CreateOrderRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<MessageResponse> getAllOrders() {
        log.info("Fetching all orders");
        MessageResponse response = orderService.getAll();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<MessageResponse> getOrderById(@PathVariable int orderId) {
        log.info("Fetching order with ID: {}", orderId);
        MessageResponse response = orderService.getId(orderId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<MessageResponse> getOrdersByUser(@PathVariable int userId) {
        log.info("Fetching orders for user with ID: {}", userId);
        MessageResponse response = orderService.OrdersByUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createOrder(@RequestBody CreateOrderRequest request) {
        log.info("Creating order for user ID: {}", request.getUserID());
        MessageResponse response = orderService.create(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
