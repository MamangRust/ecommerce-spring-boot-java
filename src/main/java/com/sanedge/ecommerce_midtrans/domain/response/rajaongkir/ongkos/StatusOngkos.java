package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.ongkos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StatusOngkos {
    @JsonProperty("code")
    private int code;

    @JsonProperty("description")
    private String description;
}
