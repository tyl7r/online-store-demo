package com.store.onlinestoredemo.core.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByProductName(String productName);

    Optional<List<Product>> findAllByProductNameContainingIgnoreCase(String productName);

    Optional<List<Product>> findAllByCategoryContainingIgnoreCase(String productName);

    Optional<List<Product>> findByPriceBetween(BigDecimal minimum, BigDecimal maximum);

    boolean existsById(Integer id);
}
