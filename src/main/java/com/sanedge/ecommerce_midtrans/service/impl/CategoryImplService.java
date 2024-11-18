package com.sanedge.ecommerce_midtrans.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.ecommerce_midtrans.domain.request.category.CreateCategoryRequest;
import com.sanedge.ecommerce_midtrans.domain.request.category.UpdateCategoryRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.domain.response.category.CategoryResponse;
import com.sanedge.ecommerce_midtrans.mapper.CategoryMapper;
import com.sanedge.ecommerce_midtrans.models.Category;
import com.sanedge.ecommerce_midtrans.repository.CategoryRepository;
import com.sanedge.ecommerce_midtrans.service.CategoryService;
import com.sanedge.ecommerce_midtrans.utils.SlugUtils;

import java.util.List;

@Service
public class CategoryImplService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryImplService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public MessageResponse getCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryResponse> categoryResponses = categoryMapper.toCategoryResponses(categories);

            return MessageResponse.builder()
                    .message("Categories fetched successfully")
                    .data(categoryResponses)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Error while fetching categories")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse getCategory(int categoryId) {
        try {
            long id = (long) categoryId;

            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));


            CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(category);

            return MessageResponse.builder()
                    .message("Category fetched successfully")
                    .data(categoryResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Error while fetching category")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse getCategorySlug(String slug) {
        try {
            Category category = categoryRepository.findBySlug(slug)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(category);

            return MessageResponse.builder()
                    .message("Category fetched successfully")
                    .data(categoryResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Error while fetching category by slug")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse createCategory(CreateCategoryRequest request) {
        try {
            Category category = new Category();

            category.setName(request.getName());
            category.setSlugCategory(SlugUtils.toSlug(request.getName()));
            category.setDescription("hello");
            category.setImageCategory(request.getFilePath());

            Category savedCategory = categoryRepository.save(category);
            CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(savedCategory);

            return MessageResponse.builder()
                    .message("Category created successfully")
                    .data(categoryResponse)
                    .statusCode(201)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Error while creating category")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse updateCategory(UpdateCategoryRequest request) {
        try {
            long id = (long) request.getId();

            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            category.setName(request.getName());
            category.setSlugCategory(SlugUtils.toSlug(request.getName()));
            category.setDescription("hello");
            category.setImageCategory(request.getFilePath());

            Category updatedCategory = categoryRepository.save(category);
            CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(updatedCategory);

            return MessageResponse.builder()
                    .message("Category updated successfully")
                    .data(categoryResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Error while updating category")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse deleteCategory(int categoryId) {
        try {

            long id = (long) categoryId;

            Category category = categoryRepository.findById(id )
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            categoryRepository.delete(category);

            return MessageResponse.builder()
                    .message("Category deleted successfully")
                    .data(null)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Error while deleting category")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }
}
