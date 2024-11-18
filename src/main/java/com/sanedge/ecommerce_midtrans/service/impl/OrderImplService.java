package com.sanedge.ecommerce_midtrans.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.ecommerce_midtrans.domain.request.order.CartItemRequest;
import com.sanedge.ecommerce_midtrans.domain.request.order.CreateOrderRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.domain.response.orderResponse.OrderResponse;
import com.sanedge.ecommerce_midtrans.exceptions.ResourceNotFoundException;
import com.sanedge.ecommerce_midtrans.mapper.OrderMapper;
import com.sanedge.ecommerce_midtrans.models.Order;
import com.sanedge.ecommerce_midtrans.models.OrderItems;
import com.sanedge.ecommerce_midtrans.models.ShippingAddress;
import com.sanedge.ecommerce_midtrans.models.User;
import com.sanedge.ecommerce_midtrans.repository.OrderItemRepository;
import com.sanedge.ecommerce_midtrans.repository.OrderRepository;
import com.sanedge.ecommerce_midtrans.repository.ShippingAddressRepository;
import com.sanedge.ecommerce_midtrans.repository.UserRepository;
import com.sanedge.ecommerce_midtrans.service.OrderService;

@Service
public class OrderImplService implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderImplService.class);

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShippingAddressRepository shippingAddressRepository;

    @Autowired
    public OrderImplService(OrderRepository orderRepository, UserRepository userRepository, 
                            OrderItemRepository orderItemRepository, 
                            ShippingAddressRepository shippingAddressRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.shippingAddressRepository = shippingAddressRepository;
    }

    @Override
    public MessageResponse getAll() {
        try {
            List<OrderResponse> orders = orderRepository.findAll()
                    .stream()
                    .map(OrderMapper::toOrderResponse)
                    .collect(Collectors.toList());

            logger.info("Successfully retrieved all orders");
            return MessageResponse.builder()
                    .message("Orders retrieved successfully")
                    .data(orders)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to retrieve orders", e);
        }
    }

    @Override
    public MessageResponse create(CreateOrderRequest request) {
        try {
            Order order = new Order();

            User user = userRepository.findById(request.getUserID())
                    .orElseThrow(() -> new ResourceNotFoundException("Failed to find user with ID: " + request.getUserID()));

           
            order.setUser(user);
            order.setName(request.getName());
            order.setPhone(request.getPhone());
            order.setCourier(request.getCourier());
            order.setEmail(user.getEmail());
            order.setShippingMethod(request.getShippingMethod());
            order.setShippingCost(request.getShippingCost());
            order.setTotalProduct(request.getTotalProduct());
            order.setTotalPrice(Integer.parseInt(request.getTotalPrice())); 
            order.setTransactionId(UUID.randomUUID().toString());

           
            Order savedOrder = orderRepository.save(order);

            
            ShippingAddress shippingAddress = new ShippingAddress();
            shippingAddress.setAlamat(request.getShippingAddress().getAlamat());
            shippingAddress.setKota(request.getShippingAddress().getKota());
            shippingAddress.setNegara(request.getShippingAddress().getNegara());
            shippingAddress.setProvinsi(request.getShippingAddress().getProvinsi());
            shippingAddress.setOrder(savedOrder);

            shippingAddressRepository.save(shippingAddress);

           
            for (CartItemRequest item : request.getCartItems()) {
                OrderItems orderItem = new OrderItems();
                orderItem.setName(item.getName());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(item.getPrice());
                orderItem.setOrder(savedOrder);

                orderItemRepository.save(orderItem);
            }

            
            OrderResponse orderResponse = OrderMapper.toOrderResponse(savedOrder);

            logger.info("Order created successfully for user ID: {}", request.getUserID());
            return MessageResponse.builder()
                    .message("Order created successfully")
                    .data(orderResponse)
                    .statusCode(201)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to create order", e);
        }
    }

    @Override
    public MessageResponse getId(int orderId) {
        try {
            Order order = orderRepository.findById((long) orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

            OrderResponse orderResponse = OrderMapper.toOrderResponse(order);

            logger.info("Order retrieved successfully for ID: {}", orderId);
            return MessageResponse.builder()
                    .message("Order retrieved successfully")
                    .data(orderResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to retrieve order", e);
        }
    }

    @Override
    public MessageResponse OrdersByUser(int userId) {
        try {
            List<OrderResponse> orders = orderRepository.findByUser_Id((long) userId)
                    .stream()
                    .map(OrderMapper::toOrderResponse)
                    .collect(Collectors.toList());

            logger.info("Orders retrieved successfully for user ID: {}", userId);
            return MessageResponse.builder()
                    .message("Orders retrieved successfully for user")
                    .data(orders)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to retrieve orders for user", e);
        }
    }

    private MessageResponse handleException(String message, Exception e) {
        logger.error(message, e);
        return MessageResponse.builder()
                .message(message)
                .data(null)
                .statusCode(500)
                .build();
    }
}
