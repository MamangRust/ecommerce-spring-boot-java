package com.sanedge.ecommerce_midtrans.service.impl;

import com.sanedge.ecommerce_midtrans.utils.Midtrans;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import com.sanedge.ecommerce_midtrans.domain.request.midtrans.CallbacksMidtrans;
import com.sanedge.ecommerce_midtrans.domain.request.midtrans.CreateMidtransRequest;
import com.sanedge.ecommerce_midtrans.domain.request.midtrans.CreditCardMidtransDetails;
import com.sanedge.ecommerce_midtrans.domain.request.midtrans.CustomerMidtransDetails;
import com.sanedge.ecommerce_midtrans.domain.request.midtrans.RequestMidtrans;
import com.sanedge.ecommerce_midtrans.domain.request.midtrans.TransactionMidtransDetails;
import com.sanedge.ecommerce_midtrans.domain.response.midtrans.SnapMidtransResponse;
import com.sanedge.ecommerce_midtrans.service.SnapClientService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SnapClientImplService implements SnapClientService {
    private Midtrans midtrans;

    public RequestMidtrans createRequest(CreateMidtransRequest request) {
        TransactionMidtransDetails transactionMidtransDetails = new TransactionMidtransDetails();
        transactionMidtransDetails.setOrderId("order-csb-" + UUID.randomUUID().toString());
        transactionMidtransDetails.setGrossAmount(request.getGrossAmount());


        CreditCardMidtransDetails creditCardMidtransDetails = new CreditCardMidtransDetails();
        creditCardMidtransDetails.setSecure(false);

        CustomerMidtransDetails customerMidtransDetails = new CustomerMidtransDetails();
        customerMidtransDetails.setFName(request.getFirstName());
        customerMidtransDetails.setLName(request.getLastName());
        customerMidtransDetails.setEmail(request.getEmail());
        customerMidtransDetails.setPhone(request.getPhone());

        CallbacksMidtrans callbacks = new CallbacksMidtrans();
        callbacks.setFinish("http://localhost:3000/user/order");

        RequestMidtrans snapRequest = new RequestMidtrans();

        snapRequest.setTransactionDetails(transactionMidtransDetails);
        snapRequest.setCreditCard(creditCardMidtransDetails);
        snapRequest.setCustomerDetails(customerMidtransDetails);
        snapRequest.setCallbacks(callbacks);

        return snapRequest;
    }

     private Map<String, Object> createRequestMap(CreateMidtransRequest request) {
        Map<String, Object> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", "order-csb-" + java.util.UUID.randomUUID());
        transactionDetails.put("gross_amount", request.getGrossAmount());

        Map<String, Object> customerDetails = new HashMap<>();
        customerDetails.put("first_name", request.getFirstName());
        customerDetails.put("last_name", request.getLastName());
        customerDetails.put("email", request.getEmail());
        customerDetails.put("phone", request.getPhone());

        Map<String, Object> callbacks = new HashMap<>();
        callbacks.put("finish", "http://localhost:3000/user/order");

        Map<String, Object> snapRequest = new HashMap<>();
        snapRequest.put("transaction_details", transactionDetails);
        snapRequest.put("customer_details", customerDetails);
        snapRequest.put("credit_card", Map.of("secure", false));
        snapRequest.put("callbacks", callbacks);

        return snapRequest;
    }

    private SnapMidtransResponse parseSnapResponse(JSONObject responseJson) {
        return SnapMidtransResponse.builder()
            .token(responseJson.optString("token"))
            .redirectUrl(responseJson.optString("redirect_url"))
            .statusCode(responseJson.optString("status_code"))
            .errorMessages(responseJson.has("error_messages")
                ? responseJson.getJSONArray("error_messages").toList()
                    .stream()
                    .map(Object::toString)
                    .toList()
                : null)
            .build();
    }

     public SnapMidtransResponse createTransaction(CreateMidtransRequest request) throws Exception {
        Map<String, Object> snapRequest = createRequestMap(request);

        try {

            MidtransSnapApi midtrans = this.midtrans.mySnapApi();
          
            JSONObject responseJson = midtrans.createTransaction(snapRequest);

           
            return parseSnapResponse(responseJson);
        } catch (MidtransError e) {
            throw new Exception("Failed to create transaction: " + e.getMessage(), e);
        }
    }
    
}
