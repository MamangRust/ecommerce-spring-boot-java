package com.sanedge.ecommerce_midtrans.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "countInStock", nullable = false)
    private int countInStock;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "weight", nullable = false)
    private int weight;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "slugProduct", nullable = false)
    private String slugProduct;
    
    @Column(name = "imageProduct", nullable = false)
    private String imageProduct;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
