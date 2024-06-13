package com.parkingcomestrue.external.config;

import com.parkingcomestrue.external.api.coordinate.CoordinateErrorHandler;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final String AUTH_HEADER = "Authorization";

    @Bean
    @Qualifier("coordinateRestTemplate")
    public RestTemplate coordinateRestTemplate(RestTemplateBuilder restTemplateBuilder,
                                               @Value("${kakao.key}") String kakaoUrl) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .errorHandler(new CoordinateErrorHandler())
                .defaultHeader(AUTH_HEADER, kakaoUrl)
                .additionalInterceptors(clientHttpRequestInterceptor())
                .build();
    }

    private ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
        return (request, body, execution) -> {
            RetryTemplate retryTemplate = new RetryTemplate();
            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));
            try {
                return retryTemplate.execute(context -> execution.execute(request, body));
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        };
    }

    @Bean
    @Qualifier("parkingApiRestTemplate")
    public RestTemplate parkingApiRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(60))
                .errorHandler(new ParkingApiErrorHandler())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .additionalInterceptors(clientHttpRequestInterceptor())
                .build();
    }
}
