package com.ari.webapp.dto;

import com.ari.webapp.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateDto {
    private String productName;
    private String productDescription;
    private String productImage;
    private BigDecimal productPrice;
    private BigDecimal productDiscount;
    private Category productCategory;
    private Integer productStock;
}
