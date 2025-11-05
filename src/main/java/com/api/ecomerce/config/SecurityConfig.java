package com.api.ecomerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**")
                        .permitAll()
                        .requestMatchers("/users/profile")
                        .authenticated()
                        .requestMatchers(HttpMethod.GET, "/categories/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/colors/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/brands/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/categories/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/colors/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/brands/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categories/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/colors/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/brands/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/colors/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/brands/**")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization");
            }
        };
    }
}
