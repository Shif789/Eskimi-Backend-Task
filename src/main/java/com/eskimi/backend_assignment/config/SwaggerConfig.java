package com.eskimi.backend_assignment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Eskimi Backend Assignment API")
                        .description("REST APIs for date calculations and weather statistics")
                        .version("v1.0"));
    }
}
