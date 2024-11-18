package com.sanedge.ecommerce_midtrans.domain.response.orderResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponses {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("user_email")
    private String userEmail;

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
}
