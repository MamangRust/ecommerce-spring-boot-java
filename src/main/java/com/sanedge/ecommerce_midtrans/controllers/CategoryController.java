package com.sanedge.ecommerce_midtrans.controllers;

import com.sanedge.ecommerce_midtrans.domain.request.category.CreateCategoryRequest;
import com.sanedge.ecommerce_midtrans.domain.request.category.UpdateCategoryRequest;
import com.sanedge.ecommerce_midtrans.domain.request.slider.CreateSliderRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.service.CategoryService;
import com.sanedge.ecommerce_midtrans.service.CloudinaryService;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public CategoryController(CategoryService categoryService, CloudinaryService cloudinaryService) {
        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/")
    public ResponseEntity<MessageResponse> getCategories() {
        log.info("Fetching all categories");
        MessageResponse response = categoryService.getCategories();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<MessageResponse> getCategoryById(@PathVariable int categoryId) {
        log.info("Fetching category with ID: {}", categoryId);
        MessageResponse response = categoryService.getCategory(categoryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<MessageResponse> getCategoryBySlug(@PathVariable String slug) {
        log.info("Fetching category with slug: {}", slug);
        MessageResponse response = categoryService.getCategorySlug(slug);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createCategory(@RequestParam("file") MultipartFile file,
            @RequestParam("name") String name) throws IOException {
        log.info("Creating a new category with name: {}", name);

        String imageUrl = cloudinaryService.uploadToCloudinary(file, name);
        log.info("Uploaded image to Cloudinary: {}", imageUrl);

        CreateCategoryRequest request = new CreateCategoryRequest();

        request.setName(name);
        request.setFilePath(imageUrl);

        MessageResponse response = categoryService.createCategory(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<MessageResponse> updateCategory(@RequestParam("id") int id,@RequestParam("file") MultipartFile file,
    @RequestParam("name") String name) throws IOException {
        log.info("Updating category with ID: {}", id);

        String imageUrl = cloudinaryService.uploadToCloudinary(file, name);
        log.info("Uploaded image to Cloudinary: {}", imageUrl);

        UpdateCategoryRequest request = new UpdateCategoryRequest();


        request.setName(name);
        request.setFilePath(imageUrl);

        MessageResponse response = categoryService.updateCategory(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable int categoryId) {
        log.info("Deleting category with ID: {}", categoryId);
        MessageResponse response = categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
