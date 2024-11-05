package com.productservice.productservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productvariant")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String variantId;
    private String color;
    private String material;
    private String Size;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
