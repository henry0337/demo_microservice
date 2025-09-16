package com.demo.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@Configuration
public class SwaggerConfiguration {
    private static final String OPENAPI_VERSION = "3.1.1";
    private static final String API_TITLE = "Demo Microservice";
    private static final String API_VERSION = "1.0";

    @Bean
    OpenAPI configureSwagger() {
        return new OpenAPI()
                .openapi(OPENAPI_VERSION)
                .info(new Info().title(API_TITLE).version(API_VERSION))
                .components(new Components().addSecuritySchemes(
                        "Bearer Token",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")));
    }
}
