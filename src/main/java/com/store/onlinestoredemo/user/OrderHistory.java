package com.store.onlinestoredemo.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_history")
@Data
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String product;
    private String productId;
    private String price;
    private String purchaseDate;

    public OrderHistory(String product, String productId, String price, String purchaseDate) {
        this.product = product;
        this.productId = productId;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }

    public OrderHistory() {

    }
}
