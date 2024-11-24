package com.sanedge.ecommerce_midtrans.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sanedge.ecommerce_midtrans.domain.response.product.ProductResponse;
import com.sanedge.ecommerce_midtrans.models.Product;

@Component
public class ProductMapper {

    public ProductResponse toProductResponse(Product product) {
        if (product == null) {
            return null;
        }

        return ProductResponse.builder()
                .id(product.getId().intValue())
                .name(product.getName())
                .description(product.getDescription())
                .slug(product.getSlugProduct())
                .brand(product.getBrand())
                .weight(product.getWeight())
                .rating((int) product.getRating())
                .imagePath(product.getImageProduct())
                .price(product.getPrice())
                .countInStock(product.getCountInStock())
                .categoryId(product.getCategory().getId().intValue())
                .createdAt(product.getCreatedAt() != null ? product.getCreatedAt().toString() : null)
                .updatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt().toString() : null)
                .build();
    }

    public List<ProductResponse> toProductResponses(List<Product> products) {
        return products.stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }
}