package com.store.onlinestoredemo.core.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String productName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Double rating;

    public String toString() {
        return String.format("%s - %s - $%.2f\n%s\nRated %d stars",
                getCategory(), getProductName(), getPrice(),
                getDescription(), getRating());
    }

    /*

Drinks - Coca Cola Zero - $1.49
A nice cold beverage
Rated 4.5 stars


     */

}
