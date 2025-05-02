package com.usa.reservas.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.usa.reservas.security.JwtAuthenticationFilter;

@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                         // ① todas las rutas
                .allowedOrigins("http://localhost:3000")   // ② origen permitido
                .allowedMethods("*")                      // ③ GET, POST, PUT, DELETE, …
                .allowedHeaders("*")                      // ④ cualquier header
                .allowCredentials(true);                  // ⑤ cookies / Authorization
    }

    
}
