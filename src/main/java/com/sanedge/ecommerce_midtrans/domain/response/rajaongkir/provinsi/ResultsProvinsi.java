package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.provinsi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResultsProvinsi {
    @JsonProperty("province_id")
    private String provinceId;

    @JsonProperty("province")
    private String province;
}
