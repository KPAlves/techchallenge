package br.com.kaique.techchallenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI techchallenge() {
        return new OpenAPI().info(
                new Info().title("Tech Challenge")
                        .description("Sistema de gestão de restaurantes")
                        .version("v0.0.1")
        );
    }
}
