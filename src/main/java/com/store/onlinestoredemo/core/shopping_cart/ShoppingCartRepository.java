package com.store.onlinestoredemo.core.shopping_cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    Optional<List<ShoppingCart>> findByUserId(Integer user_id);

    void deleteByUserId(Integer user_id);

}
