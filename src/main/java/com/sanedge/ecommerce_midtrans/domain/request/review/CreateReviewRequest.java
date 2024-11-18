package com.sanedge.ecommerce_midtrans.domain.request.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateReviewRequest {
    @JsonProperty("user_id")
    @NotNull(message = "User ID is required")
    private int userID;

    @JsonProperty("product_id") 
    @NotNull(message = "Product ID is required")
    private int productID;

    @JsonProperty("rating") 
    @NotNull(message = "Rating is required")
    @Positive(message = "Rating must be a positive number")
    private int rating;

    @JsonProperty("comment") 
    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;
}
