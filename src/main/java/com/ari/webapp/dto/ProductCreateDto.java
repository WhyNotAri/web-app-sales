package com.ari.webapp.dto;

import com.ari.webapp.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateDto {
    @NotBlank
    private String productName;

    @NotBlank
    private String productDescription;

    @NotBlank
    private String productImage;

    @NotNull
    private Double productPrice;

    @NotNull
    private Integer productStock;

    @NotBlank
    private Category productCategory;
}
