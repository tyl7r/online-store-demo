package com.store.onlinestoredemo.application;

import com.store.onlinestoredemo.api.product.ProductRequest;
import com.store.onlinestoredemo.core.product.Product;
import com.store.onlinestoredemo.core.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product addProductToDatabase(ProductRequest request) {
        return productRepository.save(mapRequestToProduct(request));
    }

    public void updateProductDetails(ProductRequest request) {
        Product oldProduct = productRepository.findByProductName(request.getProductName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        Product newProduct = mapRequestToProduct(request);
        newProduct.setId(oldProduct.getId());
        newProduct.setRating(oldProduct.getRating());
        productRepository.save(newProduct);
    }

    public void deleteProductDetails(int productId) {
        productRepository.deleteById(productId);
    }

    private Product mapRequestToProduct(ProductRequest request) {
        return Product.builder()
                .productName(request.getProductName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .rating(0.0)
                .build();
    }
}
