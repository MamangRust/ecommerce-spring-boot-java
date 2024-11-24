package com.sanedge.ecommerce_midtrans.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sanedge.ecommerce_midtrans.domain.response.orderItem.OrderItemResponse;
import com.sanedge.ecommerce_midtrans.domain.response.orderResponse.OrderResponse;
import com.sanedge.ecommerce_midtrans.domain.response.orderResponse.OrderResponses;
import com.sanedge.ecommerce_midtrans.domain.response.shippingAddress.ShippingAddressResponse;
import com.sanedge.ecommerce_midtrans.models.Order;
import com.sanedge.ecommerce_midtrans.models.OrderItems;
import com.sanedge.ecommerce_midtrans.models.ShippingAddress;

@Component
public class OrderMapper {

    public OrderResponse toOrderResponse(Order order) {
        if (order == null) {
            return null;
        }

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .name(order.getName())
                .phone(order.getPhone())
                .email(order.getEmail())
                .courier(order.getCourier())
                .shippingMethod(order.getShippingMethod())
                .shippingCost(order.getShippingCost())
                .totalProduct(order.getTotalProduct())
                .totalPrice(order.getTotalPrice())
                .transactionId(order.getTransactionId())
                .orderItems(toOrderItemsResponses(order.getOrderItems()))
                .shippingAddress(toShippingAddressResponse(order.getShippingAddress()))
                .build();
    }

    public  List<OrderResponses> toOrderResponses(List<Order> orders) {
        return orders.stream()
                .map(order -> OrderResponses.builder()
                        .id(order.getId())
                        .userId(order.getUser().getId())
                        .userName(order.getUser().getName())
                        .userEmail(order.getUser().getEmail())
                        .name(order.getName())
                        .phone(order.getPhone())
                        .email(order.getEmail())
                        .courier(order.getCourier())
                        .shippingMethod(order.getShippingMethod())
                        .shippingCost(order.getShippingCost())
                        .totalProduct(order.getTotalProduct())
                        .totalPrice(order.getTotalPrice())
                        .transactionId(order.getTransactionId())
                        .build())
                .collect(Collectors.toList());
    }

    public List<OrderItemResponse> toOrderItemsResponses(List<OrderItems> orderItems) {
        return orderItems.stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());
    }

    public  ShippingAddressResponse toShippingAddressResponse(ShippingAddress address) {
        if (address == null) {
            return null;
        }

        return ShippingAddressResponse.builder()
                .id(address.getId())
                .alamat(address.getAlamat())
                .provinsi(address.getProvinsi())
                .negara(address.getNegara())
                .kota(address.getKota())
                .build();
    }
}
