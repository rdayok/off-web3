package com.rdi.offweb3.config.security;

import com.rdi.offweb3.config.security.filters.OffWeb3AuthenticationFilter;
import com.rdi.offweb3.config.security.filters.OffWeb3AuthorizationFilter;
import com.rdi.offweb3.config.security.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static com.rdi.offweb3.config.security.utils.SecurityUtils.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final OffWeb3AuthorizationFilter eCommerceAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(httpSecurityCorsConfigurer -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedMethods(List.of("POST", "PUT", "GET"));
                    corsConfiguration.setAllowedOrigins(List.of("*"));
                })
                .addFilterAt(
                        new OffWeb3AuthenticationFilter(authenticationManager, jwtService),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(eCommerceAuthorizationFilter, OffWeb3AuthenticationFilter.class)
                .authorizeHttpRequests(
                        c -> c.requestMatchers(HttpMethod.POST, getPublicPostEndPoints().toArray(String[]::new)).permitAll()
                )
                .build();
    }

}
