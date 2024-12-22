package com.milestone.blogger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the Blogging application.
 * This class defines the security rules and permissions for different
 * endpoints.
 */
@Configuration
public class SecurityConfig {

        /**
         * Configures the security filter chain.
         *
         * @param http the {@link HttpSecurity} object to configure security settings.
         * @return the configured {@link SecurityFilterChain}.
         * @throws Exception if an error occurs during configuration.
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/", "/css/**", "/js/**", "/users/login",
                                                                "/users/register", "/users/logout", "/posts/**")
                                                .permitAll() // Public access
                                                .anyRequest().authenticated()) // Restrict other pages
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/users/login", "/users/register",
                                                                "/users/logout")) // Allow CSRF bypass for
                                // login/registration/logout
                                .logout(logout -> logout.disable()); // Disable default Spring Security logout handling

                return http.build();
        }
}