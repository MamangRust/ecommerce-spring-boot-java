package com.sanedge.ecommerce_midtrans.domain.response.shippingAddress;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShippingAddressResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("alamat")
    private String alamat;

    @JsonProperty("provinsi")
    private String provinsi;

    @JsonProperty("negara")
    private String negara;

    @JsonProperty("kota")
    private String kota;
}
