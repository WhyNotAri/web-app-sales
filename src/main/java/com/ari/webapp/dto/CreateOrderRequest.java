package com.ari.webapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {
    @NotNull
    private Long userId;

    @NotBlank(message = "cannot create an order without any item")
    private List<OrderItemDto> items;
}
