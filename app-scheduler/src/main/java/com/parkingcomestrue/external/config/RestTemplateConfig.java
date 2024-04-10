package com.parkingcomestrue.external.config;

import com.parkingcomestrue.external.coordinate.CoordinateErrorHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final String AUTH_HEADER = "Authorization";

    @Bean
    @Qualifier("coordinateRestTemplate")
    public RestTemplate coordinateRestTemplate(RestTemplateBuilder restTemplateBuilder,
                                               @Value("${kakao.key}") String kakaoUrl) {
        return restTemplateBuilder
                .errorHandler(new CoordinateErrorHandler())
                .defaultHeader(AUTH_HEADER, kakaoUrl)
                .build();
    }

    @Bean
    @Qualifier("parkingApiRestTemplate")
    public RestTemplate parkingApiRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .errorHandler(new ParkingApiErrorHandler())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
