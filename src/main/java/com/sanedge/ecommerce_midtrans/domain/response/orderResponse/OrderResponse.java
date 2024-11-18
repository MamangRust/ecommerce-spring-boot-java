package com.sanedge.ecommerce_midtrans.domain.response.orderResponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanedge.ecommerce_midtrans.domain.response.orderItem.OrderItemResponse;
import com.sanedge.ecommerce_midtrans.domain.response.shippingAddress.ShippingAddressResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("courier")
    private String courier;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_cost")
    private int shippingCost;

    @JsonProperty("total_product")
    private String totalProduct;

    @JsonProperty("total_price")
    private int totalPrice;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("order_items")
    private List<OrderItemResponse> orderItems;

    @JsonProperty("shipping_address")
    private ShippingAddressResponse shippingAddress;
}
