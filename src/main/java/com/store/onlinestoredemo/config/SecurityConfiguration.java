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
import org.springframework.util.AntPathMatcher;

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
                // ACCOUNTS Endpoints
                .requestMatchers(HttpMethod.GET, "/api/v1/account").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/account/getOrderHistory").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/account/updateInfo").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/account/getUserOrderHistory/{userId}").hasAnyRole("BUSINESS", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/account/{userId}").hasAnyRole("BUSINESS", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/account/{userId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/account/updateUserInfo").hasRole("ADMIN")


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

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
}
