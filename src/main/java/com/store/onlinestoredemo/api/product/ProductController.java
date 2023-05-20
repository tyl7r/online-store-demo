package com.store.onlinestoredemo.api.product;

import com.store.onlinestoredemo.application.ProductService;
import com.store.onlinestoredemo.core.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping("/add")
    public ResponseEntity<Product> add(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.addProductToDatabase(request));
    }

    @PutMapping("/update")
    public ResponseEntity<Product> update(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.updateProductDetails(request));
    }

    @DeleteMapping("/delete/{productId}")
    public void delete(@PathVariable int productId) {
        service.deleteProductDetails(productId);
    }

}
