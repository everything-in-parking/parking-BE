package com.parkingcomestrue.parking.config.argumentresolver;

import com.parkingcomestrue.parking.application.auth.AuthService;
import com.parkingcomestrue.parking.domain.session.MemberSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {


    private static final String JSESSIONID = "JSESSIONID";

    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        MemberAuth memberAuth = parameter.getParameterAnnotation(MemberAuth.class);
        String sessionId = webRequest.getHeader(JSESSIONID);
        if (memberAuth.nullable() && sessionId == null) {
            return null;
        }
        MemberSession session = authService.findSession(sessionId);
        return session.getMemberId();
    }
}
