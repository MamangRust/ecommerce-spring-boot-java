package com.sanedge.ecommerce_midtrans.domain.response.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("image_path")
    private String imagePath;

    @JsonProperty("price")
    private int price;

    @JsonProperty("count_in_stock")
    private int countInStock;

    @JsonProperty("category_id")
    private int categoryId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
}