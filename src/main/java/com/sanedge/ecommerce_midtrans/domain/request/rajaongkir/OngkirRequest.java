package com.sanedge.ecommerce_midtrans.domain.request.rajaongkir;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OngkirRequest {
    @JsonProperty("asal")
    private String asal;

    @JsonProperty("tujuan")
    private String tujuan;

    @JsonProperty("berat")
    private int berat;

    @JsonProperty("kurir")
    private String kurir;
}
