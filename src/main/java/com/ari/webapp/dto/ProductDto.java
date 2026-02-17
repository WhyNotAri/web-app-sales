package com.ari.webapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long productId;
    private String name;
    private Double price;
    private String image;
    private String description;
    private Integer stock;
}
