package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.provinsi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StatusProvinsi {
    @JsonProperty("code")
    private int code;

    @JsonProperty("description")
    private String description;
}
