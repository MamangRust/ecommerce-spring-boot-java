package com.sanedge.ecommerce_midtrans.controllers;

import com.sanedge.ecommerce_midtrans.domain.request.category.CreateCategoryRequest;
import com.sanedge.ecommerce_midtrans.domain.request.slider.CreateSliderRequest;
import com.sanedge.ecommerce_midtrans.domain.request.slider.UpdateSliderRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.service.CloudinaryService;
import com.sanedge.ecommerce_midtrans.service.SliderService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/slider")
public class SliderController {

    private final SliderService sliderService;
      private final CloudinaryService cloudinaryService;


    @Autowired
    public SliderController(SliderService sliderService,CloudinaryService cloudinaryService) {
        this.sliderService = sliderService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping
    public ResponseEntity<MessageResponse> getSliders() {
        log.info("Request to get all sliders");
        MessageResponse response = sliderService.getSliders();

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getSlider(@PathVariable int id) {
        log.info("Request to get slider with ID: {}", id);

        MessageResponse response = sliderService.getSlider(id);


        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createSlider(@RequestParam("file") MultipartFile file,
            @RequestParam("name") String name)throws IOException {

        CreateSliderRequest request = new CreateSliderRequest();

        String imageUrl = cloudinaryService.uploadToCloudinary(file, name);
        log.info("Uploaded image to Cloudinary: {}", imageUrl);

        request.setNama(name);
        request.setFilePath(imageUrl);

        log.info("Request to create slider with data: {}", request);

        MessageResponse response = sliderService.create(request);


        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateSlider(@RequestParam("id") int id,@RequestParam("file") MultipartFile file,
    @RequestParam("name") String name) throws IOException {
        UpdateSliderRequest request = new UpdateSliderRequest();

        String imageUrl = cloudinaryService.uploadToCloudinary(file, name);
        log.info("Uploaded image to Cloudinary: {}", imageUrl);

        request.setNama(name);
        request.setFilePath(imageUrl);

        
        log.info("Request to update slider with ID: {} and data: {}", id, request);
     
        MessageResponse response = sliderService.update(request);

        
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteSlider(@PathVariable int id) {
        log.info("Request to delete slider with ID: {}", id);
        MessageResponse response = sliderService.delete(id);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
