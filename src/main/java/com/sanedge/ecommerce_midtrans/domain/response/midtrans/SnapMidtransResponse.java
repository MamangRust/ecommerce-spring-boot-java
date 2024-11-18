package com.sanedge.ecommerce_midtrans.domain.response.midtrans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SnapMidtransResponse {
    private String token;

    @JsonProperty("redirect_url")
    private String redirectUrl;

    @JsonProperty("status_code")
    private String statusCode; 

    @JsonProperty("error_messages")
    private List<String> errorMessages; 
}
