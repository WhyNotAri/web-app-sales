package com.ari.webapp.dto;

import com.ari.webapp.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String image;
    private String description;
    private Integer stock;
    private Category category;

    public ProductDto(String name, BigDecimal price, String image, String description, Integer stock, Category category) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.stock = stock;
        this.category = category;
    }
}
