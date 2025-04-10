package com.br.monitoria.software.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:8081"
                            ,"https://arquitetura-gamified-cartela.vercel.app"
                            ,"https://arquitetura-gamified-cartela-pkir2wtsf.vercel.app"
                            ,"https://arquitetura-gamified-cartela-489c5nohr.vercel.app") // URL do seu front-end
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}