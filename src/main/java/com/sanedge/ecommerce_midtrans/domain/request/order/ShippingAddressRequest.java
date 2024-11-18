package com.sanedge.ecommerce_midtrans.domain.request.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShippingAddressRequest {
    @JsonProperty("alamat")
    private String alamat;

    @JsonProperty("provinsi") 
    private String provinsi;

    @JsonProperty("kota") 
    private String kota;

    @JsonProperty("negara")
    private String negara;
}
