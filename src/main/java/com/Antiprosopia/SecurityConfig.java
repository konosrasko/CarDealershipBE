package com.Antiprosopia;

import com.Antiprosopia.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/dealership/signup").permitAll()
                .requestMatchers("/api/citizen/signup").permitAll()
                .requestMatchers("/api/cars").permitAll()
                .requestMatchers("/api/carsForDelearship").permitAll()
                .requestMatchers("/api/user/role/dealership").hasAnyAuthority(UserRole.ROLE_DEALERSHIP.name())
                .requestMatchers("/api/reservation/test-drive").permitAll()
                .requestMatchers("/api/purchase").permitAll()
                .requestMatchers("/api/carData").permitAll()
                .requestMatchers("/api/car/update").permitAll()
                .requestMatchers("/api/reservations").permitAll()
                .requestMatchers("/api/get-id").permitAll()
                .requestMatchers("/api/delete").permitAll()
                .requestMatchers("/api/car/add").permitAll()
                .requestMatchers("/api/car/renew/**").hasAnyAuthority(UserRole.ROLE_DEALERSHIP.name())
                .anyRequest().authenticated();
        return http.build();
    }
}

