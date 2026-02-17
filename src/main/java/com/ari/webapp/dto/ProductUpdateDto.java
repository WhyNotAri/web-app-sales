package com.ari.webapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateDto {
    private String name;
    private String description;
    private String image;
    private String price;
    private BigDecimal discount;
}
