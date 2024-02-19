package com.example.parking.external.config.interceptor;

import com.example.parking.auth.AuthService;
import com.example.parking.auth.MemberSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String JSESSIONID = "JSESSIONID";

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String sessionId = request.getHeader(JSESSIONID);
        MemberSession session = authService.findSession(sessionId);
        authService.updateSessionExpiredAt(session);
        return true;
    }
}
