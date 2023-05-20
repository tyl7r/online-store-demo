package com.store.onlinestoredemo.application;

import com.store.onlinestoredemo.core.order.Order;
import com.store.onlinestoredemo.core.order.OrderRepository;
import com.store.onlinestoredemo.core.product.Product;
import com.store.onlinestoredemo.core.product.ProductRepository;
import com.store.onlinestoredemo.core.shopping_cart.ShoppingCart;
import com.store.onlinestoredemo.core.shopping_cart.ShoppingCartRepository;
import com.store.onlinestoredemo.core.user.User;
import com.store.onlinestoredemo.core.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final ShoppingCartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    //GET /store/search?name="Banana"&category="Food"&priceRange="0-50"&sortBy="Name/Price/Rating"
    public List<Product> search(String name, String category, String priceRange, String sortBy) {
        List<Product> products = new ArrayList<>();
        if (name != null) {
            products.addAll(productRepository.findAllByProductNameContainingIgnoreCase(name)
                    .orElseThrow());
        }
        if (category != null) {
            products.addAll(productRepository.findAllByCategoryContainingIgnoreCase(category)
                    .orElseThrow());
        }

        if (priceRange != null) {
            String[] numbers = validateCorrectPriceRange(priceRange);
            BigDecimal minimum = new BigDecimal(Integer.parseInt(numbers[0]));
            BigDecimal maximum = new BigDecimal(Integer.parseInt(numbers[1]));
            products.addAll(productRepository.findByPriceBetween(minimum, maximum)
                    .orElseThrow());
        }

        if (sortBy != null) {
            switch (sortBy) {
                case "Name", "name":
                    products.sort(Comparator.comparing(Product::getProductName));
                    break;
                case "Rating", "rating":
                    products.sort(Comparator.comparing(Product::getRating));
                    break;
                case "Price", "price":
                    products.sort(Comparator.comparing(Product::getPrice));
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect 'sortBy' method given");
            }
        }
        return products;
    }

    public ShoppingCart addToCart(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        User user = getAuthenticatedUser();
//        ShoppingCart cartItem = new ShoppingCart(user, product);
        var cartItem = ShoppingCart.builder()
                .user(user)
                .product(product)
                .build();
        cartRepository.save(cartItem);
        return cartItem;
    }

    public List<ShoppingCart> getCart() {
        User user = getAuthenticatedUser();
        return cartRepository.findByUserId(user.getId()).orElseThrow();
    }

    // Get authenticated user -> get id -> search for rows in cart for user_id

    // Loop through cart -> save order to repository -> delete cart and send response
    @Transactional
    public List<Order> processOrder() {
        User user = getAuthenticatedUser();
        List<ShoppingCart> shoppingCart = cartRepository.findByUserId(user.getId())
                .orElseThrow();

        if (!shoppingCart.isEmpty()) {
            List<Order> orders = new ArrayList<>();
            Order order;

            for (ShoppingCart cart : shoppingCart) {
                order = new Order(LocalDateTime.now(), cart.getProduct(), user);
                orders.add(order);
                orderRepository.save(order);
            }

            cartRepository.deleteByUserId(user.getId());
            return orders;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
    }

    private User getAuthenticatedUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(userEmail)
                .orElseThrow();
    }

    private String[] validateCorrectPriceRange(String priceRange) {
        String regex = "\\d+-\\d+";
        String[] numbers = priceRange.split("-");
        int minimum = Integer.parseInt(numbers[0]);
        int maximum = Integer.parseInt(numbers[1]);
        if (priceRange.matches(regex) && minimum < maximum) {
            return numbers;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect price range given.");
    }
}
