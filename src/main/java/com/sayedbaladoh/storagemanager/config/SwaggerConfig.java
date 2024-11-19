package com.sayedbaladoh.storagemanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info =
        @Info(
                title = "${spring.application.name} API",
                version = "${spring.application.version}",
                description = "API documentation for the ${spring.application.description}"))
public class SwaggerConfig {
}
