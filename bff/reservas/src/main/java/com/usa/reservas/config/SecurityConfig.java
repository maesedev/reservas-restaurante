package com.usa.reservas.config;

import com.usa.reservas.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          // 1) API stateless
          .csrf(csrf -> csrf.disable())
          .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .cors(Customizer.withDefaults())

          // 2) Reglas de acceso con wildcards
          .authorizeHttpRequests(auth -> auth
              // → Bloquea el POST /api/v1/login
              .requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll()

              // → Exige JWT en **TODAS** las rutas bajo /api/v1/…
              .requestMatchers("/api/v1/**").authenticated()

              // → (Opcional) permite todo lo que no sea /api/v1/**
              .anyRequest().permitAll()
          )

          // 3) Inserta tu filtro JWT antes del UsernamePasswordAuthenticationFilter
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
