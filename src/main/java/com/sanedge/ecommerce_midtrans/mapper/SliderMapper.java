package com.sanedge.ecommerce_midtrans.mapper;


import com.sanedge.ecommerce_midtrans.domain.response.slider.SliderResponse;
import com.sanedge.ecommerce_midtrans.models.Slider;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class SliderMapper {

    public SliderResponse toSliderResponse(Slider slider) {
        if (slider == null) {
            return null;
        }

        return SliderResponse.builder()
                .id(slider.getId())
                .name(slider.getName())
                .image(slider.getImage())
                .createdAt(slider.getCreatedAt() != null ? slider.getCreatedAt().toString() : null)
                .updatedAt(slider.getUpdatedAt() != null ? slider.getUpdatedAt().toString() : null)
                .build();
    }

    public  List<SliderResponse> toSliderResponses(List<Slider> sliders) {
        if (sliders == null || sliders.isEmpty()) {
            return List.of();
        }

        return sliders.stream()
                .map(this::toSliderResponse)
                .collect(Collectors.toList());
    }
}
