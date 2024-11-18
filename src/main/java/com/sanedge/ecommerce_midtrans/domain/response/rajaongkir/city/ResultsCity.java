package com.sanedge.ecommerce_midtrans.domain.response.rajaongkir.city;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResultsCity {
    @JsonProperty("city_id")
    private String cityId;

    @JsonProperty("province_id")
    private String provinceId;

    @JsonProperty("province")
    private String province;

    @JsonProperty("type")
    private String type;

    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("postal_code")
    private String postalCode;
}
