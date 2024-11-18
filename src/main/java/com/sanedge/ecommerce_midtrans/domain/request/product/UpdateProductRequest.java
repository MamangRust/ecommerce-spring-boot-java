package com.sanedge.ecommerce_midtrans.domain.request.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProductRequest {
    @JsonProperty("id")  
    @NotNull(message = "Product ID is required")
    private int id;

    @JsonProperty("name")  
    private String name;

    @JsonProperty("category_id")  
    private int categoryID;

    @JsonProperty("description")  
    private String description;

    @JsonProperty("price")  
    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be positive or zero")
    private int price;

    @JsonProperty("count_in_stock")
    @NotNull(message = "Count in stock is required")
    @PositiveOrZero(message = "Stock count must be positive or zero")
    private int countInStock;

    @JsonProperty("weight") 
    @NotNull(message = "Product weight is required")
    @PositiveOrZero(message = "Weight must be positive or zero")
    private int weight;

    @JsonProperty("rating") 
    private int rating;

    @JsonProperty("brand")  
    private String brand;

    @JsonProperty("file_path")
    private String filePath;
}
