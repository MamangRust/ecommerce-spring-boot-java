package com.sanedge.ecommerce_midtrans.domain.request.slider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateSliderRequest {
    @JsonProperty("id") 
    @NotNull(message = "ID is required")
    private int id;

    @JsonProperty("nama")
    private String nama;

    @JsonProperty("file_path")
    private String filePath;
}
