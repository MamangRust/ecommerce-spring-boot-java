package com.sanedge.ecommerce_midtrans.service;

import com.sanedge.ecommerce_midtrans.domain.request.auth.RegisterRequest;
import com.sanedge.ecommerce_midtrans.domain.request.user.UpdateUserRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;

public interface UserService {

    public MessageResponse getUsers();

    public MessageResponse createUser(RegisterRequest request);

    public MessageResponse getUser(int id);

    public MessageResponse updateUser(UpdateUserRequest request);

    public MessageResponse deleteUser(int id);
}
