package com.sanedge.ecommerce_midtrans.service;



import com.sanedge.ecommerce_midtrans.domain.request.midtrans.CreateMidtransRequest;
import com.sanedge.ecommerce_midtrans.domain.request.midtrans.RequestMidtrans;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.domain.response.midtrans.SnapMidtransResponse;

public interface SnapClientService {
    public MessageResponse createRequest(CreateMidtransRequest request);
    public SnapMidtransResponse createTransaction(CreateMidtransRequest request) throws Exception;
}
