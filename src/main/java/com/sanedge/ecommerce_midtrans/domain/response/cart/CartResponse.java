package com.sanedge.ecommerce_midtrans.domain.response.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private String price;

    @JsonProperty("image")
    private String image;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("product_id")
    private Long productId;  
}