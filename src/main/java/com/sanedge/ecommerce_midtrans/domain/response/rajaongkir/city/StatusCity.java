package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.city;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StatusCity {
    @JsonProperty("code")
    private int code;


    @JsonProperty("description")
    private String description;
}
