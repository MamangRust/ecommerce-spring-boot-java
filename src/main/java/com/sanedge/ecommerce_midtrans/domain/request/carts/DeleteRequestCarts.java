package com.sanedge.ecommerce_midtrans.domain.request.carts;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteRequestCarts {
    @JsonProperty("cart_ids")
    private List<Integer> cartIds;
}
