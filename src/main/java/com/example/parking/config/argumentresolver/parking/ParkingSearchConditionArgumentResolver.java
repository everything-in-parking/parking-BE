package com.example.parking.config.argumentresolver.parking;

import com.example.parking.application.SearchConditionMapper;
import com.example.parking.application.parking.dto.ParkingSearchConditionRequest;
import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayType;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class ParkingSearchConditionArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int BASE_HOURS = 1;
    private static final String NOT_FREE = "유료";
    private static final String RECOMMEND_ORDER_CONDITION = "추천 순";

    private final SearchConditionMapper searchConditionMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ParkingSearchCondition.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String[] operationTypes = webRequest.getParameterValues("operationTypes");
        String[] parkingTypes = webRequest.getParameterValues("parkingTypes");
        String feeType = webRequest.getParameter("feeTypes");
        String[] payTypes = webRequest.getParameterValues("payTypes");
        Integer hours = Integer.parseInt(webRequest.getParameter("hours"));
        String priority = webRequest.getParameter("priority");

        if (containsNull(operationTypes, parkingTypes, feeType, payTypes, hours)) {
            return defaultRequest();
        }

        return new ParkingSearchConditionRequest(
                toList(operationTypes),
                toList(parkingTypes),
                feeType,
                toList(payTypes),
                hours,
                priority
        );
    }

    private boolean containsNull(String[] operationTypes, String[] parkingTypes, String feeType,
                                 String[] payTypes, Integer hours) {
        return operationTypes == null || parkingTypes == null || feeType == null || payTypes == null || hours == null;
    }

    private ParkingSearchConditionRequest defaultRequest() {
        return new ParkingSearchConditionRequest(
                searchConditionMapper.getValues(OperationType.class),
                searchConditionMapper.getValues(ParkingType.class),
                NOT_FREE,
                searchConditionMapper.getValues(PayType.class),
                BASE_HOURS,
                RECOMMEND_ORDER_CONDITION
        );
    }

    private List<String> toList(String[] parameters) {
        return Arrays.stream(parameters)
                .toList();
    }
}
