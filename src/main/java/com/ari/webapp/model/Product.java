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
    private Double price;
    private BigDecimal discount;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
