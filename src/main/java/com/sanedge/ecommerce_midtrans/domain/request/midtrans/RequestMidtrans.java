package com.sanedge.ecommerce_midtrans.domain.request.midtrans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestMidtrans {
    @JsonProperty("transaction_details")
    private TransactionMidtransDetails transactionDetails;

    @JsonProperty("credit_card")
    private CreditCardMidtransDetails creditCard;

    @JsonProperty("customer_details")
    private CustomerMidtransDetails customerDetails;

    @JsonProperty("callbacks")
    private CallbacksMidtrans callbacks;
}