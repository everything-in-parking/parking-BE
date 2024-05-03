package com.parkingcomestrue.parking.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "주차의 상상은 현실이 된다 aka 주상현", description = "주차의 상상은 현실이 된다 aka 주상현 API 명세서"))
@Configuration
public class SwaggerConfig {

    private static final String JSESSIONID = "JSESSIONID";

    @Value("${api-prefix}")
    private String API_PREFIX;

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme auth = new SecurityScheme()
                .type(Type.APIKEY).in(In.COOKIE).name(JSESSIONID);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(JSESSIONID);

        return new OpenAPI().addServersItem(new Server().url(API_PREFIX))
                .components(new Components().addSecuritySchemes(JSESSIONID, auth))
                .addSecurityItem(securityRequirement);
    }
}
