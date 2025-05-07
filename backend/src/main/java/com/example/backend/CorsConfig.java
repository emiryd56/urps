package com.example.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Tüm endpointlere izin ver
                        .allowedOrigins("http://localhost:3000") // Frontend domaini
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // İzin verilen HTTP metodları
                        .allowedHeaders("*") // Tüm header'lara izin ver
                        .allowCredentials(true); // Tarayıcı tarafında kimlik doğrulama bilgilerini destekle
            }
        };
    }
}
