package com.sanedge.ecommerce_midtrans.domain.response.dashboard;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardResponse {
    @JsonProperty("user")
    private int user;

    @JsonProperty("product")
    private int product;

    @JsonProperty("order")
    private int order;

    @JsonProperty("pendapatan")
    private int pendapatan;

    @JsonProperty("total_pendapatan")
    private List<Integer> totalPendapatan;
}
