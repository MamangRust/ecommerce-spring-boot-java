package com.sanedge.ecommerce_midtrans.service;



import com.sanedge.ecommerce_midtrans.domain.request.order.CreateOrderRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;

public interface OrderService {
    public MessageResponse getAll();
    public MessageResponse create(CreateOrderRequest request);
    public MessageResponse getId(int orderId);
    public MessageResponse OrdersByUser(int userId);


}
