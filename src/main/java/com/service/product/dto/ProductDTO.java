package com.service.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private String id;
    @NotBlank(message = "product name is required")
    private String name;
    @NotBlank(message = "product description is required")
    private String description;
    @NotNull(message = "product cost is required")
    private Long cost;
    @NotNull(message = "product cost is required")
    private Integer quantity;
    private Long totalCost;
}
