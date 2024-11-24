package com.sanedge.ecommerce_midtrans.controllers;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.sanedge.ecommerce_midtrans.domain.request.category.CreateCategoryRequest;
import com.sanedge.ecommerce_midtrans.domain.request.category.UpdateCategoryRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.service.CategoryService;
import com.sanedge.ecommerce_midtrans.service.CloudinaryService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

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

    @GetMapping
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

    @PostMapping("/update")
    public ResponseEntity<MessageResponse> updateCategory(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "name", required = true) String name) throws IOException {

        log.info("ID: {}", id);
        log.info("Name: {}", name);
        log.info("File: {}", file != null ? file.getOriginalFilename() : "null");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        request.getParameterMap().forEach((key, value) -> log.info("Param: {} = {}", key, Arrays.toString(value)));

        String imageUrl = cloudinaryService.uploadToCloudinary(file, name);
        log.info("Uploaded image to Cloudinary: {}", imageUrl);

        UpdateCategoryRequest requestDto = new UpdateCategoryRequest();

        requestDto.setId(id);
        requestDto.setName(name);
        requestDto.setFilePath(imageUrl);

        MessageResponse response = categoryService.updateCategory(requestDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable int categoryId) {
        log.info("Deleting category with ID: {}", categoryId);
        MessageResponse response = categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
