package com.store.onlinestoredemo.core.order;

import com.store.onlinestoredemo.core.product.Product;
import com.store.onlinestoredemo.core.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Lombok should be creating this but the code wouldn't compile without
    public Order(LocalDateTime purchaseDate, Product product, User user) {
        this.purchaseDate = purchaseDate;
        this.product = product;
        this.user = user;
    }
}
