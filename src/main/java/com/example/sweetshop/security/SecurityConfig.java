package com.example.sweetshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll() // public auth endpoints
                .requestMatchers(HttpMethod.DELETE, "/api/sweets/**").hasRole("ADMIN") // admin-only delete
                .requestMatchers(HttpMethod.POST, "/api/sweets/*/restock").hasRole("ADMIN") // admin-only restock
                .anyRequest().authenticated() // all other endpoints require login
                .and()
                .httpBasic(); // for simplicity, or replace with JWT filter later
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // for simplicity, not for production
    }
}


