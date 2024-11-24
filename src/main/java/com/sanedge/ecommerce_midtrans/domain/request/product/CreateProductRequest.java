package com.sanedge.ecommerce_midtrans.domain.request.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateProductRequest {
    @JsonProperty("name") 
    @NotBlank(message = "Product name is required")
    private String name;

    @JsonProperty("category_id") 
    @NotBlank(message = "Category ID is required")
    private long categoryID;

    @JsonProperty("description")  
    @NotBlank(message = "Product description is required")
    private String description;

    @JsonProperty("price") 
    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be positive or zero")
    private int price;

    @JsonProperty("brand") 
    @NotBlank(message = "Brand is required")
    private String brand;

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

    @JsonProperty("file") 
    @NotBlank(message = "File path is required")
    private String filePath;
}
