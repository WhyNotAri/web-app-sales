package com.ari.webapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDto {
    @NotNull
    private Long productId;

    @NotBlank
    private String productName;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal price;
}
