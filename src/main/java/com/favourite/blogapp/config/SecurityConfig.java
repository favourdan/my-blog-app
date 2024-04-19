package com.favourite.blogapp.config;

import com.favourite.blogapp.security.JWTAuthenticationPoint;
import com.favourite.blogapp.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JWTAuthenticationPoint jwtAuthenticationPoint;
    private final JwtAuthenticationFilter authenticationFilter;
@Bean
    public PasswordEncoder passwordEncoder(){

    return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->csrf.disable()).authorizeHttpRequests((authorize)->
                authorize.requestMatchers(HttpMethod.GET,"/api/v1/**").permitAll()
                        .requestMatchers("api/v1/auth/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling( exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationPoint))
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
    }





}
