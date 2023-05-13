package com.store.onlinestoredemo.core.shopping_cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

//    Optional<User> findByEmail(String email);

}
