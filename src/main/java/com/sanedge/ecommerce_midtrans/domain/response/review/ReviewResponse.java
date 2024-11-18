package com.sanedge.ecommerce_midtrans.domain.response.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanedge.ecommerce_midtrans.domain.response.user.UserResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponse {
    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("user")
    private UserResponse user;

    @JsonProperty("product_id")
    private long productId;
}