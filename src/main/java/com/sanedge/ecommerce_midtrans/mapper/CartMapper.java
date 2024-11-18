package com.sanedge.ecommerce_midtrans.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.sanedge.ecommerce_midtrans.domain.response.cart.CartResponse;
import com.sanedge.ecommerce_midtrans.models.Cart;

public class CartMapper {

    public static CartResponse toCartResponse(Cart cart) {
        if (cart == null) {
            return null;
        }

        return CartResponse.builder()
                .id(cart.getId())
                .name(cart.getName())
                .price(cart.getPrice())
                .image(cart.getImage())
                .quantity(cart.getQuantity())
                .weight(cart.getWeight())
                .userId(cart.getUser() != null ? cart.getUser().getId() : null)
                .productId(cart.getProduct() != null ? cart.getProduct().getId() : null)
                .build();
    }

    public static List<CartResponse> toCartResponses(List<Cart> carts) {
        if (carts == null || carts.isEmpty()) {
            return List.of();
        }

        return carts.stream()
                .map(CartMapper::toCartResponse)
                .collect(Collectors.toList());
    }
}