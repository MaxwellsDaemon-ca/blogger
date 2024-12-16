package com.milestone.blogger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/", "/css/**", "/js/**", "/users/login", "/users/register").permitAll() // Allow public access
                                .anyRequest().authenticated() // Require login for all other routes
                )
                .formLogin(login -> login
                                .loginPage("/users/login")
                                .permitAll()
                )
                .logout(logout -> logout
                                .logoutUrl("/users/logout")
                                .logoutSuccessUrl("/")
                );

        return http.build();
    }
}
