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

    /*  ## Nice client response, removed due to breaking code and unused. ##

    public String toString() {
        return String.format("%s - %s - $%.2f\n%s\nRated %d stars",
                getCategory(), getProductName(), getPrice(),
                getDescription(), getRating());
    }
     */
}
