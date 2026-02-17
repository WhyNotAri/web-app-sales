package com.ari.webapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    @NotBlank
    private String name;

    @NotBlank
    private Double price;

    @NotNull
    private Integer stock;
}
