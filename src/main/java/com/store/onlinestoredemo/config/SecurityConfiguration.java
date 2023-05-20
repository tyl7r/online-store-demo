package com.store.onlinestoredemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_BUSINESS = "BUSINESS";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                // ACCOUNTS Endpoints (below should use Lambda format)
                .requestMatchers(HttpMethod.GET, "/api/v1/account").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/account/getOrderHistory").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/account/updateInfo").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/account/getUserOrderHistory/{userId}").hasAnyRole(ROLE_BUSINESS, ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/v1/account/{userId}").hasAnyRole(ROLE_BUSINESS, ROLE_ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/api/v1/account/{userId}").hasRole(ROLE_ADMIN)
                .requestMatchers(HttpMethod.PUT, "/api/v1/account/updateUserInfo").hasRole(ROLE_ADMIN)
                //STORE
                .requestMatchers("/api/v1/store/**").authenticated()
                // PRODUCTS
                .requestMatchers("/api/v1/product/**").hasAnyRole(ROLE_BUSINESS, ROLE_ADMIN)
                // AUTH
                .requestMatchers("/api/v1/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
