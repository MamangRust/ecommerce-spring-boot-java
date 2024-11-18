package com.sanedge.ecommerce_midtrans.domain.request.category;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UpdateCategoryRequest {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name") 
    private String name;

    @JsonProperty("file_path")
    private String filePath;
}
