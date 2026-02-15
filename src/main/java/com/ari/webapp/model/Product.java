package com.ari.webapp.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String image;
    private Integer price;
    private String discount;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
