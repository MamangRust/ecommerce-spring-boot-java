package com.sanedge.ecommerce_midtrans.domain.request.carts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateRequestCart {
    @JsonProperty("name") 
    private String name;

    @JsonProperty("price")  
    private String price;

    @JsonProperty("image_product")  
    private String imageProduct;

    @JsonProperty("quantity")  
    private int quantity;

    @JsonProperty("product_id")  
    private int productId;

    @JsonProperty("user_id")  
    private int userId;

    @JsonProperty("weight") 
    private int weight;
}
