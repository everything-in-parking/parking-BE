package com.parkingcomestrue.parking.config.argumentresolver.parking;

import com.parkingcomestrue.parking.application.parking.dto.ParkingQueryRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ParkingQueryArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ParkingQuery.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return new ParkingQueryRequest(
                Double.valueOf(webRequest.getParameter("longitude")),
                Double.valueOf(webRequest.getParameter("latitude")),
                Integer.parseInt(webRequest.getParameter("radius"))
        );
    }
}
