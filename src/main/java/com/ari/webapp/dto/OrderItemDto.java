package com.ari.webapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    @NotNull
    private Long productId;

    @NotNull(message = "cannot acquire zero value from an item")
    private Integer quantity;
}
