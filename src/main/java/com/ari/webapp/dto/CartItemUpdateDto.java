package com.ari.webapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemUpdateDto {
    @NotNull
    @Min(1)
    private Integer quantity;
}