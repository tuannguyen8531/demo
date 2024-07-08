package com.dev.server.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {
    @Value("${swagger.version}")
    private String appVersion;

    @Value("${swagger.title}")
    private String appTitle;

    @Value("${swagger.description}")
    private String appDescription;

    @Value("${swagger.dev.url}")
    private String appDevUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components().addSecuritySchemes(
                "api",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .name("Authorization")
            ))
            .servers(List.of(new Server().url(appDevUrl)))
            .info(new io.swagger.v3.oas.models.info.Info()
                .title(appTitle)
                .version(appVersion)
                .description(appDescription)
                .license(new io.swagger.v3.oas.models.info.License()
                    .name("Apache 2.0")
                    .url("http://www.apache.org/licenses/LICENSE-2.0.html")
                )
            );
    }
}
