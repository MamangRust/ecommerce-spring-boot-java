package com.sanedge.ecommerce_midtrans.domain.request.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCategoryRequest {
    @JsonProperty("name")  
    @NotNull(message = "Category name is required")  
    private String name;

    @JsonProperty("file") 
    @NotNull(message = "File path is required") 
    private String filePath;

}
