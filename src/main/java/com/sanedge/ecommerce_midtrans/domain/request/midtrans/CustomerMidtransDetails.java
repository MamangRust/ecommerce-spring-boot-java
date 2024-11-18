package com.sanedge.ecommerce_midtrans.domain.request.midtrans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerMidtransDetails {
    @JsonProperty("first_name")
    private String fName;

    @JsonProperty("last_name")
    private String lName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;
}
