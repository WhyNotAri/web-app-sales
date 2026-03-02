package com.ari.webapp.dto;

import com.ari.webapp.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

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
    private BigDecimal productPrice;

    @NotNull
    private Integer productStock;

    @NotNull(message = "Must be part of a category")
    private Category productCategory;
}
