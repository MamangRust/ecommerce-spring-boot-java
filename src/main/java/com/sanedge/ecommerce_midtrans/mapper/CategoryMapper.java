package com.sanedge.ecommerce_midtrans.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sanedge.ecommerce_midtrans.domain.response.category.CategoryResponse;
import com.sanedge.ecommerce_midtrans.models.Category;

@Component
public class CategoryMapper {

    public CategoryResponse toCategoryResponse(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .slug(category.getSlugCategory())
                .imagePath(category.getImageCategory())
                .createdAt(category.getCreatedAt().toString())
                .updatedAt(category.getUpdatedAt().toString())
                .build();
    }

    public List<CategoryResponse> toCategoryResponses(List<Category> categories) {
        return categories.stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }
}
