package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()//Création d’un objet OpenAPI
                .info(new Info()
                        .title("Purchase and Supplier Management API")//Titre affiché dans Swagger UI
                        .version("1.0")
                        .description("API for managing purchases and suppliers"));
    }
}