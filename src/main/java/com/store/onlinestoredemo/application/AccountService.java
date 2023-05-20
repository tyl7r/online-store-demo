package com.store.onlinestoredemo.application;

import com.store.onlinestoredemo.api.account.AccountRequest;
import com.store.onlinestoredemo.api.account.AccountResponse;
import com.store.onlinestoredemo.core.order.Order;
import com.store.onlinestoredemo.core.order.OrderRepository;
import com.store.onlinestoredemo.core.user.User;
import com.store.onlinestoredemo.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ResponseStatusException userNotFoundException = new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

    public AccountResponse retrieveUserDetails() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> userNotFoundException);
        return mapObjectToResponse(user);
    }

    public AccountResponse adminRetrieveUserDetails(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> userNotFoundException);
        return mapObjectToResponse(user);
    }


    // /updateInfo + /updateUserInfo both use this method, hence implementing the 'isAdminRequest' boolean to ensure
    // the correct logic with retrieving user details and updating role if necessary.
    public AccountResponse updateUserDetails(AccountRequest request, boolean isAdminRequest) {
        User user;
        if (isAdminRequest) {
            user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> userNotFoundException);
            user.setRole(request.getRole());
        } else {
            user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> userNotFoundException);
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        userRepository.save(user);
        return mapObjectToResponse(user);
    }

    // Method used by both the User to see personal Order History, and Business/Admins to view
    // other peoples' data. If userId is null, it will be a user - otherwise it's an admin request
    public String retrieveUserOrderHistory(Integer userId) {
        List<Order> userOrders;
        if (userId == null) {
            User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> userNotFoundException);
            userOrders = orderRepository.findOrdersByUserId(user.getId()).orElseThrow(() -> userNotFoundException);
        } else {
            userOrders = orderRepository.findOrdersByUserId(userId).orElseThrow(() -> userNotFoundException);
        }

        StringBuilder sb = new StringBuilder();
        userOrders.forEach(order -> sb.append("Purchase Date: ").append(order.getPurchaseDate()).append("\n").append(order.getProduct().toString()).append("\n+-----------------------+\n"));
        return sb.toString();
    }

    public void deleteUserDetails(int userId) {
        userRepository.deleteById(userId);
    }

    private AccountResponse mapObjectToResponse(User user) {
        return AccountResponse.builder().firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail()).role(user.getRole()).build();
    }
}
