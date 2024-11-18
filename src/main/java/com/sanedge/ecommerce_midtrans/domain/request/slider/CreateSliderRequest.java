package com.sanedge.ecommerce_midtrans.domain.request.slider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSliderRequest {
    @JsonProperty("nama")
    @NotBlank(message = "Slider name is required")
    private String nama;

    @JsonProperty("file_path")
    @NotBlank(message = "File path is required")
    private String filePath;
}
