package com.store.onlinestoredemo.api.store;

import com.store.onlinestoredemo.application.StoreService;
import com.store.onlinestoredemo.core.order.Order;
import com.store.onlinestoredemo.core.product.Product;
import com.store.onlinestoredemo.core.shopping_cart.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService service;

    //GET /store/search?name="Banana"&category="Food"&priceRange="0-50"&sortBy="Name/Price/Rating"
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String category,
                                                @RequestParam(required = false) String priceRange,
                                                @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(service.search(name, category, priceRange, sortBy));
    }

    //todo Global - Map responses for client - client should not see User info / IDs
    @PostMapping("/cart/{productId}")
    public ResponseEntity<ShoppingCart> addToCart(@PathVariable Integer productId) {
        return ResponseEntity.ok(service.addToCart(productId));
    }

    @GetMapping("/cart")
    public ResponseEntity<List<ShoppingCart>> getCart() {
        return ResponseEntity.ok(service.getCart());
    }

    @PostMapping("/process-order")
    public ResponseEntity<List<Order>> processOrder() {
        return ResponseEntity.ok(service.processOrder());
    }
}
