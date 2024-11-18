package com.sanedge.ecommerce_midtrans.domain.request.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemRequest {
    @JsonProperty("name")  
    private String name;

    @JsonProperty("quantity")  
    private int quantity;

    @JsonProperty("price") 
    private int price;
}
