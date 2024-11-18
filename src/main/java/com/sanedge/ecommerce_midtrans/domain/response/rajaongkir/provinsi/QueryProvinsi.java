package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.provinsi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QueryProvinsi {
    @JsonProperty("id")
    private String id;
}
