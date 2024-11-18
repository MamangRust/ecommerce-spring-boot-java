package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.ongkos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourierResultOngkos {
    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("costs")
    private List<CostOngkos> costs;
}
