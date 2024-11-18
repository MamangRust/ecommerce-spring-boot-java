package com.sanedge.ecommerce_midtrans.service;


import com.sanedge.ecommerce_midtrans.domain.request.category.CreateCategoryRequest;
import com.sanedge.ecommerce_midtrans.domain.request.category.UpdateCategoryRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;


public interface CategoryService {
    public MessageResponse getCategories();
    public MessageResponse getCategory(int categoryId);
    public MessageResponse getCategorySlug(String slug);
    public MessageResponse createCategory(CreateCategoryRequest request);
    public MessageResponse updateCategory(UpdateCategoryRequest request);
    public MessageResponse deleteCategory(int catId);
}
