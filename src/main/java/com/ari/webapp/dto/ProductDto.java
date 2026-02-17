package com.ari.webapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    @NotNull
    private Long id;

    @NotBlank (message = "product must have a name")
    private String name;

    @NotNull (message = "product must have a price")
    private Double price;

    @NotNull
    private Integer stock;
}
