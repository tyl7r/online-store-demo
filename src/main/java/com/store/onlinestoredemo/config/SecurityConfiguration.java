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


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/account/**").hasRole("ADMIN") //deleteAccountById Admin
                .requestMatchers(HttpMethod.PUT, "/api/v1/account/updateInfo/**").hasRole("ADMIN") //updateById Admin
                .requestMatchers(HttpMethod.GET, "/api/v1/account").authenticated() // retrieveUserDetails User
                .requestMatchers(HttpMethod.GET, "/api/v1/account/**").hasAnyRole("BUSINESS", "ADMIN") // retrieveUserDetails Business
                .requestMatchers(HttpMethod.GET, "/api/v1/account/getOrderHistory/**").hasAnyRole("BUSINESS", "ADMIN") // retrieveUserOrders Business
                .requestMatchers("/api/v1/product/**")
                .hasAnyRole("BUSINESS", "ADMIN")
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
