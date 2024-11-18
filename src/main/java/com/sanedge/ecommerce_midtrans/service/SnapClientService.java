package com.sanedge.ecommerce_midtrans.service;



import com.sanedge.ecommerce_midtrans.domain.request.midtrans.CreateMidtransRequest;
import com.sanedge.ecommerce_midtrans.domain.request.midtrans.RequestMidtrans;
import com.sanedge.ecommerce_midtrans.domain.response.midtrans.SnapMidtransResponse;

public interface SnapClientService {
    public RequestMidtrans createRequest(CreateMidtransRequest request);
    public SnapMidtransResponse createTransaction(CreateMidtransRequest request) throws Exception;
}
