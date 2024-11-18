package com.sanedge.ecommerce_midtrans.service;


import com.sanedge.ecommerce_midtrans.domain.request.carts.CreateRequestCart;
import com.sanedge.ecommerce_midtrans.domain.request.product.CreateProductRequest;
import com.sanedge.ecommerce_midtrans.domain.request.product.UpdateProductRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;

public interface ProductService {
    public MessageResponse getAllProducts();
    public MessageResponse createProduct(CreateProductRequest createProductRequest);
    public MessageResponse getProductById(Long productId);
    public MessageResponse getProductBySlug(String slug);
    public MessageResponse updateProduct(UpdateProductRequest updateProductRequest);
    public MessageResponse deleteProduct(Long productId);
    public MessageResponse updateProductQuantity(CreateRequestCart cart);
}
