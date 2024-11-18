package com.sanedge.ecommerce_midtrans.service;



import com.sanedge.ecommerce_midtrans.domain.request.carts.CreateRequestCart;
import com.sanedge.ecommerce_midtrans.domain.request.carts.DeleteRequestCarts;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;


public interface CartService {
    public MessageResponse findAllUserByID(int userId);
    public MessageResponse create(CreateRequestCart request);
    public MessageResponse delete(int cartId);
    public MessageResponse deleteMany(DeleteRequestCarts cartIds);
}
