package com.sanedge.ecommerce_midtrans.service.impl;

import com.sanedge.ecommerce_midtrans.domain.request.carts.CreateRequestCart;
import com.sanedge.ecommerce_midtrans.domain.request.product.CreateProductRequest;
import com.sanedge.ecommerce_midtrans.domain.request.product.UpdateProductRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.models.Category;
import com.sanedge.ecommerce_midtrans.models.Product;
import com.sanedge.ecommerce_midtrans.repository.CategoryRepository;
import com.sanedge.ecommerce_midtrans.repository.ProductRepository;
import com.sanedge.ecommerce_midtrans.service.ProductService;
import com.sanedge.ecommerce_midtrans.utils.SlugUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;

    }

    @Override
    public MessageResponse getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            return MessageResponse.builder()
                    .message("Products retrieved successfully")
                    .data(products)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to retrieve products", e);
        }
    }

    @Override
    public MessageResponse getProductBySlug(String slug) {
        try {
            Optional<Product> product = productRepository.findBySlugProduct(slug);
            if (product.isEmpty()) {
                throw new RuntimeException("Product with slug " + slug + " not found");
            }
            return MessageResponse.builder()
                    .message("Product retrieved successfully")
                    .data(product.get())
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to retrieve product by slug", e);
        }
    }

    @Override
    public MessageResponse createProduct(CreateProductRequest request) {
        try {
            long catId = (long) request.getCategoryID();

            Category category = categoryRepository.findById(catId)
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

            return MessageResponse.builder()
                    .message("Product created successfully")
                    .data(savedProduct)
                    .statusCode(201)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to create product", e);
        }
    }

    @Override
    public MessageResponse getProductById(Long productId) {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                throw new RuntimeException("Product with ID " + productId + " not found");
            }
            return MessageResponse.builder()
                    .message("Product retrieved successfully")
                    .data(product.get())
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

            Optional<Product> existingProduct = productRepository.findById(id);
            if (existingProduct.isEmpty()) {
                throw new RuntimeException("Product with ID " + request.getId() + " not found");
            }

            Category category = categoryRepository.findById(catId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Product product = existingProduct.get();
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

            Product updatedProduct = productRepository.save(product);

            return MessageResponse.builder()
                    .message("Product updated successfully")
                    .data(updatedProduct)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return handleException("Failed to update product", e);
        }
    }

    @Override
    public MessageResponse deleteProduct(Long productId) {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                throw new RuntimeException("Product with ID " + productId + " not found");
            }

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

            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) {
                throw new RuntimeException("Product with ID " + id + " not found");
            }

            Product updatedProduct = product.get();
            updatedProduct.setCountInStock(cart.getQuantity());

            productRepository.save(updatedProduct);

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
