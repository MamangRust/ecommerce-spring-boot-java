package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.ongkos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QueryOngkos {
    @JsonProperty("origin")
    private String origin;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("courier")
    private String courier;
}
