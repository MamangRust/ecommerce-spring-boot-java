package com.sanedge.ecommerce_midtrans.domain.request.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrderRequest {
    @JsonProperty("user_id")
    private Long userID;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("courier")
    private String courier;

    @JsonProperty("shipping_address")
    private ShippingAddressRequest shippingAddress;

    @JsonProperty("cart_items")
    private List<CartItemRequest> cartItems;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_cost")
    private int shippingCost;

    @JsonProperty("total_product")
    private String totalProduct;

    @JsonProperty("total_price")
    private String totalPrice;
}
