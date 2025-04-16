package com.example.pokemonapp.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csfr -> csfr.disable()) // Cross-site Request Forgery
                .authorizeHttpRequests(authorizeHttpRequest ->
                        authorizeHttpRequest.anyRequest().authenticated() // Authenticated = logged and in Security Context
                )
                .httpBasic(withDefaults()); // Just HTTP authentication, not HTTPS

        return http.build();
    }
}
