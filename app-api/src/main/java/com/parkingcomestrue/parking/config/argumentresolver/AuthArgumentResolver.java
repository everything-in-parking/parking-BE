package com.parkingcomestrue.parking.config.argumentresolver;

import com.parkingcomestrue.common.domain.session.MemberSession;
import com.parkingcomestrue.parking.application.auth.AuthService;
import com.parkingcomestrue.parking.application.member.dto.MemberId;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
        return parameter.getParameterType().equals(MemberId.class) &&
                parameter.hasParameterAnnotation(MemberAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        MemberAuth memberAuth = parameter.getParameterAnnotation(MemberAuth.class);
        String sessionId = getJsessionid((HttpServletRequest) webRequest.getNativeRequest());
        if (memberAuth.nullable() && sessionId == null) {
            return MemberId.from(-1L);
        }
        MemberSession session = authService.findSession(sessionId);
        return MemberId.from(session.getMemberId());
    }

    private String getJsessionid(HttpServletRequest request) {
        if (request == null || request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(JSESSIONID)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
