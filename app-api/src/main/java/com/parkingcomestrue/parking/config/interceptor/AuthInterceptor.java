package com.parkingcomestrue.parking.config.interceptor;

import com.parkingcomestrue.parking.application.auth.AuthService;
import com.parkingcomestrue.common.domain.session.MemberSession;
import com.parkingcomestrue.parking.support.exception.ClientException;
import com.parkingcomestrue.parking.support.exception.ClientExceptionInformation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private static final String JSESSIONID = "JSESSIONID";

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("request: {}", request.getRequestURL());
        String sessionId = getJsessionid(request);
        authService.findAndUpdateSession(sessionId);
        return true;
    }

    private String getJsessionid(HttpServletRequest request) {
        for(Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(JSESSIONID)) {
                return cookie.getValue();
            }
        }
        throw new ClientException(ClientExceptionInformation.UNAUTHORIZED);
    }
}
