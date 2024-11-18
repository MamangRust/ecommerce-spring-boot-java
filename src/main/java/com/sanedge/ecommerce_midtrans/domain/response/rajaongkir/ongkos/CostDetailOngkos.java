package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.ongkos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CostDetailOngkos {
    @JsonProperty("value")
    private int value;

    @JsonProperty("etd")
    private String etd;

    @JsonProperty("note")
    private String note;
}
