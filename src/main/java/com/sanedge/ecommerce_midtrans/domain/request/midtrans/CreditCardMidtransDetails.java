package com.sanedge.ecommerce_midtrans.domain.request.midtrans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardMidtransDetails {
    @JsonProperty("secure")
    private boolean secure;
}
