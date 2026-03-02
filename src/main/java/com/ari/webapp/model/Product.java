package com.ari.webapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer stock;
    private String image;
    private BigDecimal price;

    @Column(precision = 5, scale = 2)
    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    private Category category;
}
