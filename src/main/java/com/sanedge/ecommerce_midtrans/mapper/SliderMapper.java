package com.sanedge.ecommerce_midtrans.mapper;


import com.sanedge.ecommerce_midtrans.domain.response.slider.SliderResponse;
import com.sanedge.ecommerce_midtrans.models.Slider;
import java.util.List;
import java.util.stream.Collectors;

public class SliderMapper {

    public static SliderResponse toSliderResponse(Slider slider) {
        if (slider == null) {
            return null;
        }

        return SliderResponse.builder()
                .id(slider.getId())
                .name(slider.getName())
                .image(slider.getImage())
                .build();
    }

    public static List<SliderResponse> toSliderResponses(List<Slider> sliders) {
        if (sliders == null || sliders.isEmpty()) {
            return List.of();
        }

        return sliders.stream()
                .map(SliderMapper::toSliderResponse)
                .collect(Collectors.toList());
    }
}
