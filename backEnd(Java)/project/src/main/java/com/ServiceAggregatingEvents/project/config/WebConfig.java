package com.ServiceAggregatingEvents.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite toate cererile CORS
                .allowedOrigins("http://localhost:4200") // URL-ul frontend-ului
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metodele permise
                .allowedHeaders("*") // Permite toate headerele
                .allowCredentials(true); // Permite autentificarea
    }
}

