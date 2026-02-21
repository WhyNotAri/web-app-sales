package com.ari.webapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCreateDto {
    @NotNull
    private Long userId;

    @NotEmpty
    private List<OrderItemCreateDto> items;
}
