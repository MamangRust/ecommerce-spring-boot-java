package com.sanedge.ecommerce_midtrans.service.impl;

import com.sanedge.ecommerce_midtrans.domain.request.slider.CreateSliderRequest;
import com.sanedge.ecommerce_midtrans.domain.request.slider.UpdateSliderRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.domain.response.slider.SliderResponse;
import com.sanedge.ecommerce_midtrans.models.Slider;
import com.sanedge.ecommerce_midtrans.repository.SliderRepository;
import com.sanedge.ecommerce_midtrans.service.SliderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SliderServiceImpl implements SliderService {

    private final Logger logger = LoggerFactory.getLogger(SliderServiceImpl.class);
    private final SliderRepository sliderRepository;

    @Autowired
    public SliderServiceImpl(SliderRepository sliderRepository) {
        this.sliderRepository = sliderRepository;
    }

    @Override
    public MessageResponse getSliders() {
        logger.info("Starting getSliders() method");
        try {
            List<Slider> sliders = sliderRepository.findAll();
            if (sliders.isEmpty()) {
                logger.warn("No sliders found");
                return MessageResponse.builder()
                        .message("No sliders found")
                        .statusCode(404)
                        .build();
            }

            logger.info("Retrieved {} sliders successfully", sliders.size());
            List<SliderResponse> sliderResponses = sliders.stream()
                    .map(slider -> SliderResponse.builder()
                            .id(slider.getId())
                            .name(slider.getName())
                            .image(slider.getImage())
                            .build())
                    .collect(Collectors.toList());

            return MessageResponse.builder()
                    .message("Sliders retrieved successfully")
                    .data(sliderResponses)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            logger.error("Error occurred while retrieving sliders: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to retrieve sliders")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse getSlider(int sliderId) {
        logger.info("Starting getSlider() method with ID: {}", sliderId);
        try {
            long id = (long) sliderId;

            Slider slider = sliderRepository.findById(id).orElseThrow(() -> new RuntimeException("Slider not found"));

            logger.info("Successfully retrieved slider with ID: {}", sliderId);
            SliderResponse sliderResponse = SliderResponse.builder()
                    .id(slider.getId())
                    .name(slider.getName())
                    .image(slider.getImage())
                    .build();

            return MessageResponse.builder()
                    .message("Slider retrieved successfully")
                    .data(sliderResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            logger.error("Error occurred while retrieving slider by ID: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to retrieve slider by ID")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse create(CreateSliderRequest createSliderRequest) {
        logger.info("Starting createSlider() method with request: {}", createSliderRequest);
        try {
            Slider newSlider = new Slider();
            newSlider.setName(createSliderRequest.getNama());
            newSlider.setImage(createSliderRequest.getFilePath());

            Slider savedSlider = sliderRepository.save(newSlider);
            logger.info("Slider created successfully with ID: {}", savedSlider.getId());

            SliderResponse sliderResponse = SliderResponse.builder()
                    .id(savedSlider.getId())
                    .name(savedSlider.getName())
                    .image(savedSlider.getImage())
                    .build();

            return MessageResponse.builder()
                    .message("Slider created successfully")
                    .data(sliderResponse)
                    .statusCode(201)
                    .build();
        } catch (Exception e) {
            logger.error("Error occurred while creating slider: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to create slider")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse update(UpdateSliderRequest updateSliderRequest) {
        logger.info("Starting updateSlider() method with request: {}", updateSliderRequest);
        try {
            long id = (long) updateSliderRequest.getId();


            Slider slider = sliderRepository.findById(id).orElseThrow(() -> new RuntimeException("Slider not found"));
           

            slider.setName(updateSliderRequest.getNama());
            slider.setImage(updateSliderRequest.getFilePath());

            Slider updatedSlider = sliderRepository.save(slider);
            logger.info("Slider with ID {} updated successfully", updatedSlider.getId());

            SliderResponse sliderResponse = SliderResponse.builder()
                    .id(updatedSlider.getId())
                    .name(updatedSlider.getName())
                    .image(updatedSlider.getImage())
                    .build();

            return MessageResponse.builder()
                    .message("Slider updated successfully")
                    .data(sliderResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            logger.error("Error occurred while updating slider: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to update slider")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse delete(int sliderId) {
        logger.info("Starting deleteSlider() method with ID: {}", sliderId);
        try {
            long id = (long) sliderId;

            Slider slider = sliderRepository.findById(id).orElseThrow(() -> new RuntimeException("Slider not found"));
            if (slider == null) {
                logger.warn("Slider with ID {} not found", sliderId);
                return MessageResponse.builder()
                        .message("Slider not found")
                        .statusCode(404)
                        .build();
            }

            sliderRepository.delete(slider);
            logger.info("Slider with ID {} deleted successfully", sliderId);

            return MessageResponse.builder()
                    .message("Slider deleted successfully")
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            logger.error("Error occurred while deleting slider: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to delete slider")
                    .statusCode(500)
                    .build();
        }
    }
}
