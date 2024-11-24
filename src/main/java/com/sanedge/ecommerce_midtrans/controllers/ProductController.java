package com.sanedge.ecommerce_midtrans.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sanedge.ecommerce_midtrans.domain.request.carts.CreateRequestCart;
import com.sanedge.ecommerce_midtrans.domain.request.product.CreateProductRequest;
import com.sanedge.ecommerce_midtrans.domain.request.product.UpdateProductRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.models.Product;
import com.sanedge.ecommerce_midtrans.service.ProductService;

import lombok.extern.slf4j.Slf4j;

import com.sanedge.ecommerce_midtrans.service.CloudinaryService;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ProductController(ProductService productService, CloudinaryService cloudinaryService) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MessageResponse> getProductById(@PathVariable Long id) {
        log.info("Fetching product by ID: {}", id);
        MessageResponse response = productService.getProductById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<MessageResponse> getAllProducts() {
        log.info("Fetching all products");
        MessageResponse response = productService.getAllProducts();

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<MessageResponse> getProductBySlug(@PathVariable String slug) {
        log.info("Fetching product by slug: {}", slug);
        MessageResponse response = productService.getProductBySlug(slug);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("weight") int weight,
            @RequestParam("brand") String brand,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("countInStock") int countInStock,
            @RequestParam("rating") double rating) throws IOException {

        log.info("Name: {}", name);
        log.info("Description: {}", description);
        log.info("Price: {}", price);
        log.info("Weight: {}", weight);
        log.info("Brand: {}", brand);
        log.info("CategoryId: {}", categoryId);
        log.info("CountInStock: {}", countInStock);
        log.info("Rating: {}", rating);
        log.info("File: {}", file.getOriginalFilename());

        log.info("Creating new product: {}", name);

        String imageUrl = cloudinaryService.uploadToCloudinary(file, name);
        log.info("Uploaded image to Cloudinary: {}", imageUrl);

        CreateProductRequest request = new CreateProductRequest();

        request.setName(name);
        request.setDescription(description);
        request.setFilePath(imageUrl);
        request.setPrice((int) price);
        request.setWeight(weight);
        request.setBrand(brand);
        request.setCategoryID((long) categoryId);
        request.setCountInStock(countInStock);
        request.setRating((int) rating);

        MessageResponse response = productService.createProduct(request);
        log.info("Product created with ID: {}",
                response.getData() != null ? ((Product) response.getData()).getId() : "N/A");
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<MessageResponse> updateProduct(
            @RequestParam("id") Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("weight") int weight,
            @RequestParam("brand") String brand,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("countInStock") int countInStock,
            @RequestParam("rating") double rating) throws IOException {

        log.info("Updating product with ID: {}", id);
        log.info("Name: {}", name);
        log.info("Description: {}", description);
        log.info("Price: {}", price);
        log.info("Weight: {}", weight);
        log.info("Brand: {}", brand);
        log.info("CategoryId: {}", categoryId);
        log.info("CountInStock: {}", countInStock);
        log.info("Rating: {}", rating);
        log.info("File: {}", file.getOriginalFilename());

        String imageUrl = cloudinaryService.uploadToCloudinary(file, name);
        log.info("Uploaded updated image to Cloudinary: {}", imageUrl);

        UpdateProductRequest request = new UpdateProductRequest();

      
        request.setId(id);
        request.setName(name);
        request.setDescription(description);
        request.setFilePath(imageUrl);
        request.setPrice((int) price);
        request.setWeight(weight);
        request.setBrand(brand);
        request.setCategoryID(categoryId);
        request.setCountInStock(countInStock);
        request.setRating((int) rating);

        MessageResponse response = productService.updateProduct(request);
        log.info("Product updated with ID: {}", id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable Long productId) {
        log.info("Deleting product with ID: {}", productId);
        MessageResponse response = productService.deleteProduct(productId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/update-quantity")
    public ResponseEntity<MessageResponse> updateProductQuantity(@RequestBody CreateRequestCart cart) {
        log.info("Updating product quantity for product ID: {}", cart.getProductId());
        MessageResponse response = productService.updateProductQuantity(cart);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
