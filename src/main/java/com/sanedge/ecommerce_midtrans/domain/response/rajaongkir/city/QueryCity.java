package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.city;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QueryCity {
    @JsonProperty("province")
    private String province;

    @JsonProperty("id")
    private String id;
}
