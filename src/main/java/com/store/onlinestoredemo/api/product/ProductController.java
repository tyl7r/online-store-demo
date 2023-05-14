package com.store.onlinestoredemo.api.product;

import com.store.onlinestoredemo.application.ProductService;
import com.store.onlinestoredemo.core.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

//    POST /product/add (ProductRequest) - Add product info
    @PostMapping("/add")
    public ResponseEntity<Product> add(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.addProductToDatabase(request));
    }

    // PUT /product/update?productId=123 (ProductRequest) - Update product info
    @PutMapping("/update")
    public ResponseEntity<ProductRequest> update(@RequestBody ProductRequest request) {
        service.updateProductDetails(request);
        return ResponseEntity.ok(request);
    }

    // DEL /product?productId=123 - Delete Product by Id
    @DeleteMapping("/delete/{productId}")
    public void delete(@PathVariable int productId) {
        service.deleteProductDetails(productId);
    }

}
