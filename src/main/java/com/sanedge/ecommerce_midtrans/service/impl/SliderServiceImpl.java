package com.sanedge.ecommerce_midtrans.service.impl;

import com.sanedge.ecommerce_midtrans.domain.request.slider.CreateSliderRequest;
import com.sanedge.ecommerce_midtrans.domain.request.slider.UpdateSliderRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.domain.response.slider.SliderResponse;
import com.sanedge.ecommerce_midtrans.mapper.SliderMapper;
import com.sanedge.ecommerce_midtrans.models.Slider;
import com.sanedge.ecommerce_midtrans.repository.SliderRepository;
import com.sanedge.ecommerce_midtrans.service.SliderService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SliderServiceImpl implements SliderService {

   
    private final SliderRepository sliderRepository;
    private final SliderMapper sliderMapper;

    @Autowired
    public SliderServiceImpl(SliderRepository sliderRepository, SliderMapper sliderMapper) {
        this.sliderRepository = sliderRepository;
        this.sliderMapper = sliderMapper;
    }

    @Override
    public MessageResponse getSliders() {
        log.info("Starting getSliders() method");
        try {
            List<Slider> sliders = sliderRepository.findAll();
            if (sliders.isEmpty()) {
                log.warn("No sliders found");
                return MessageResponse.builder()
                        .message("No sliders found")
                        .statusCode(404)
                        .build();
            }

            log.info("Retrieved {} sliders successfully", sliders.size());
            List<SliderResponse> sliderResponses = sliderMapper.toSliderResponses(sliders);

            return MessageResponse.builder()
                    .message("Sliders retrieved successfully")
                    .data(sliderResponses)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while retrieving sliders: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to retrieve sliders")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse getSlider(int sliderId) {
        log.info("Starting getSlider() method with ID: {}", sliderId);
        try {
            long id = (long) sliderId;

            Slider slider = sliderRepository.findById(id).orElseThrow(() -> new RuntimeException("Slider not found"));

            log.info("Successfully retrieved slider with ID: {}", sliderId);
            SliderResponse sliderResponse = sliderMapper.toSliderResponse(slider);



            return MessageResponse.builder()
                    .message("Slider retrieved successfully")
                    .data(sliderResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while retrieving slider by ID: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to retrieve slider by ID")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse create(CreateSliderRequest createSliderRequest) {
        log.info("Starting createSlider() method with request: {}", createSliderRequest);
        try {
            Slider newSlider = new Slider();
            newSlider.setName(createSliderRequest.getNama());
            newSlider.setImage(createSliderRequest.getFilePath());

            Slider savedSlider = sliderRepository.save(newSlider);
            log.info("Slider created successfully with ID: {}", savedSlider.getId());

            SliderResponse sliderResponse = sliderMapper.toSliderResponse(savedSlider);

            return MessageResponse.builder()
                    .message("Slider created successfully")
                    .data(sliderResponse)
                    .statusCode(201)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while creating slider: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to create slider")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse update(UpdateSliderRequest updateSliderRequest) {
        log.info("Starting updateSlider() method with request: {}", updateSliderRequest);
        try {
            long id = (long) updateSliderRequest.getId();


            Slider slider = sliderRepository.findById(id).orElseThrow(() -> new RuntimeException("Slider not found"));
           

            slider.setName(updateSliderRequest.getNama());
            slider.setImage(updateSliderRequest.getFilePath());

            Slider updatedSlider = sliderRepository.save(slider);
            log.info("Slider with ID {} updated successfully", updatedSlider.getId());

            SliderResponse sliderResponse = sliderMapper.toSliderResponse(updatedSlider);

            return MessageResponse.builder()
                    .message("Slider updated successfully")
                    .data(sliderResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while updating slider: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to update slider")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse delete(int sliderId) {
        log.info("Starting deleteSlider() method with ID: {}", sliderId);
        try {
            long id = (long) sliderId;

            Slider slider = sliderRepository.findById(id).orElseThrow(() -> new RuntimeException("Slider not found"));
            if (slider == null) {
                log.warn("Slider with ID {} not found", sliderId);
                return MessageResponse.builder()
                        .message("Slider not found")
                        .statusCode(404)
                        .build();
            }

            sliderRepository.delete(slider);
            log.info("Slider with ID {} deleted successfully", sliderId);

            return MessageResponse.builder()
                    .message("Slider deleted successfully")
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while deleting slider: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to delete slider")
                    .statusCode(500)
                    .build();
        }
    }
}
