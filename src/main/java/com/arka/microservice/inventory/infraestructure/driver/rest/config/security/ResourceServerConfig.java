package com.arka.microservice.inventory.infraestructure.driver.rest.config.security;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@AllArgsConstructor
@EnableReactiveMethodSecurity
public class ResourceServerConfig {
    private static final String[] WHITE_LIST_OPENAPI = {"/swagger", "/swagger/**", "/api-docs", "/api-docs/**", "/webjars/**"};

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ReactiveJwtDecoder jwtDecoder,
                                                         ReactiveJwtAuthenticationConverter jwtAuthenticationConverter) {

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(WHITE_LIST_OPENAPI).permitAll()
                        // Endpoints GET para productos: accesibles a roles "client" y "admin"
                        .pathMatchers(HttpMethod.GET, "/api/storage", "/api/storage/{id}","/api/supply","/api/supply/{id}")
                        .hasAnyRole("client", "admin")
                        // Endpoints que requieren el rol admin: POST, PUT y DELETE
                        .pathMatchers(HttpMethod.POST, "/api/storage/**","/api/warehouse","/api/supply/**").hasRole("admin")
                        .pathMatchers(HttpMethod.PUT, "/api/storage/**","/api/supply/**").hasRole("admin")
                        .pathMatchers(HttpMethod.DELETE, "/api/storage/**","/api/supply/**").hasRole("admin")
                        // Para cualquier otra petición se requiere autenticación
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtDecoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                        )
                )
                .build();
    }
}