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
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().permitAll())
                    .csrf(csrf -> csrf.disable())
                    .formLogin(form -> form.disable());

                return http.build();
        }
}