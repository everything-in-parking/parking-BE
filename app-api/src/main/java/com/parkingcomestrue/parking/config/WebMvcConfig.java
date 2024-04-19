package com.parkingcomestrue.parking.config;

import com.parkingcomestrue.parking.config.argumentresolver.AuthArgumentResolver;
import com.parkingcomestrue.parking.config.argumentresolver.parking.ParkingQueryArgumentResolver;
import com.parkingcomestrue.parking.config.argumentresolver.parking.ParkingSearchConditionArgumentResolver;
import com.parkingcomestrue.parking.config.interceptor.AuthInterceptor;
import io.swagger.v3.oas.models.PathItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final AuthArgumentResolver authArgumentResolver;
    private final ParkingQueryArgumentResolver parkingQueryArgumentResolver;
    private final ParkingSearchConditionArgumentResolver parkingSearchConditionArgumentResolver;

    @Value("${cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(List.of(
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/signup",
                        "/signin",
                        "/parkings/**",
                        "/actuator/**"
                ));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
        resolvers.add(parkingQueryArgumentResolver);
        resolvers.add(parkingSearchConditionArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(
                        PathItem.HttpMethod.OPTIONS.name(),
                        PathItem.HttpMethod.GET.name(),
                        PathItem.HttpMethod.POST.name(),
                        PathItem.HttpMethod.PUT.name(),
                        PathItem.HttpMethod.DELETE.name(),
                        PathItem.HttpMethod.PATCH.name()
                )
                .allowCredentials(true)
                .exposedHeaders("*");
    }
}
