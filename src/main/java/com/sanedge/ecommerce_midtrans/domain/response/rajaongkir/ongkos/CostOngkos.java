package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.ongkos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CostOngkos {
     @JsonProperty("service")
    private String service;

    @JsonProperty("description")
    private String description;

    @JsonProperty("cost")
    private List<CostDetailOngkos> costDetails;
}
