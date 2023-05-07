package com.store.onlinestoredemo.auth;

import com.store.onlinestoredemo.config.JwtService;
import com.store.onlinestoredemo.user.OrderHistory;
import com.store.onlinestoredemo.user.Role;
import com.store.onlinestoredemo.user.User;
import com.store.onlinestoredemo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .orderHistory(Collections.emptyList())
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void test() {
        List<OrderHistory> orderHistory = Arrays.asList(
                new OrderHistory("MacBook Pro", "123", "899.99", "2023-05-07"),
                new OrderHistory("Bananas", "456", "1.59", "2023-05-08"),
                new OrderHistory("Garden Chair", "789", "45.00", "2023-05-09")
        );
        var user = User.builder()
                .firstName("Tyler")
                .lastName("Cherry")
                .email("tyler@tyler.com")
                .password(passwordEncoder.encode("poop"))
                .role(Role.BUSINESS)
                .orderHistory(orderHistory)
                .build();
        repository.save(user);
    }
}
