package com.sanedge.ecommerce_midtrans.service.impl;

import com.sanedge.ecommerce_midtrans.domain.request.carts.CreateRequestCart;
import com.sanedge.ecommerce_midtrans.domain.request.product.CreateProductRequest;
import com.sanedge.ecommerce_midtrans.domain.request.product.UpdateProductRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.domain.response.product.ProductResponse;
import com.sanedge.ecommerce_midtrans.mapper.ProductMapper;
import com.sanedge.ecommerce_midtrans.models.Category;
import com.sanedge.ecommerce_midtrans.models.Product;
import com.sanedge.ecommerce_midtrans.repository.CategoryRepository;
import com.sanedge.ecommerce_midtrans.repository.ProductRepository;
import com.sanedge.ecommerce_midtrans.service.ProductService;
import com.sanedge.ecommerce_midtrans.utils.SlugUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;

    }

    @Override
    public MessageResponse getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            List<ProductResponse>productResponses = productMapper.toProductResponses(products);


            return MessageResponse.builder()
                    .message("Products retrieved successfully")
                    .data(productResponses)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to retrieve products", e);
        }
    }

    @Override
    public MessageResponse getProductBySlug(String slug) {
        try {
            Product product = productRepository.findBySlugProduct(slug).orElseThrow(()-> new RuntimeException("product not found"));

            ProductResponse productResponse = productMapper.toProductResponse(product);

           
            return MessageResponse.builder()
                    .message("Product retrieved successfully")
                    .data(productResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to retrieve product by slug", e);
        }
    }

    @Override
    public MessageResponse createProduct(CreateProductRequest request) {
        try {

            Category category = categoryRepository.findById(request.getCategoryID())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Product product = new Product();
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setSlugProduct(SlugUtils.toSlug(request.getName()));
            product.setImageProduct(request.getFilePath());
            product.setPrice(request.getPrice());
            product.setWeight(request.getWeight());
            product.setBrand(request.getBrand());
            product.setCategory(category);
            product.setCountInStock(request.getCountInStock());
            product.setRating(request.getRating());

            Product savedProduct = productRepository.save(product);

            ProductResponse productResponse = productMapper.toProductResponse(savedProduct);

            return MessageResponse.builder()
                    .message("Product created successfully")
                    .data(productResponse)
                    .statusCode(201)
                    .build();
        } catch (Exception e) {
            log.error("error: {}", e.toString());

            return handleException("Failed to create product", e);
        }
    }

    @Override
    public MessageResponse getProductById(Long productId) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("product not found"));

            ProductResponse productResponse = productMapper.toProductResponse(product);

            log.info("product: {}", productResponse);
            
            return MessageResponse.builder()
                    .message("Product retrieved successfully")
                    .data(productResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to retrieve product by ID", e);
        }
    }

    @Override
    public MessageResponse updateProduct(UpdateProductRequest request) {
        try {
            long id = (long) request.getId();
            long catId = (long) request.getCategoryID();

            Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));

            Category category = categoryRepository.findById(catId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            
            existingProduct.setName(request.getName());
            existingProduct.setDescription(request.getDescription());
            existingProduct.setSlugProduct(SlugUtils.toSlug(request.getName()));
            existingProduct.setImageProduct(request.getFilePath());
            existingProduct.setPrice(request.getPrice());
            existingProduct.setWeight(request.getWeight());
            existingProduct.setBrand(request.getBrand());
            existingProduct.setCategory(category);
            existingProduct.setCountInStock(request.getCountInStock());
            existingProduct.setRating(request.getRating());

            Product updatedProduct = productRepository.save(existingProduct);

            ProductResponse productResponse = productMapper.toProductResponse(updatedProduct);

            return MessageResponse.builder()
                    .message("Product updated successfully")
                    .data(productResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to update product", e);
        }
    }

    @Override
    public MessageResponse deleteProduct(Long productId) {
        try {
            productRepository.findById(productId).orElseThrow(() -> new RuntimeException("product not found"));

            

            productRepository.deleteById(productId);

            return MessageResponse.builder()
                    .message("Product deleted successfully")
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to delete product", e);
        }
    }

    @Override
    public MessageResponse updateProductQuantity(CreateRequestCart cart) {
        try {
            long id = (long) cart.getProductId();

            Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));

            existingProduct.setCountInStock(cart.getQuantity());

            productRepository.save(existingProduct);

            return MessageResponse.builder()
                    .message("Product quantity updated successfully")
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to update product quantity", e);
        }
    }

    private MessageResponse handleException(String message, Exception e) {

        return MessageResponse.builder()
                .message(message)
                .data(null)
                .statusCode(500)
                .build();
    }
}
