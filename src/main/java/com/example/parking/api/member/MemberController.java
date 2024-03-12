package com.example.parking.api.member;

import com.example.parking.application.member.MemberService;
import com.example.parking.application.member.dto.MemberLoginRequest;
import com.example.parking.application.member.dto.MemberSignupRequest;
import com.example.parking.application.member.dto.PasswordChangeRequest;
import com.example.parking.auth.AuthService;
import com.example.parking.external.config.argumentresolver.MemberAuth;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private static final String JSESSIONID = "JSESSIONID";

    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping("/users")
    public ResponseEntity<Void> signup(@RequestBody MemberSignupRequest request) {
        memberService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(HttpServletResponse httpServletResponse,
                                      @RequestBody MemberLoginRequest request) {
        Long memberId = memberService.login(request);
        String sessionId = authService.createSession(memberId);
        httpServletResponse.addCookie(new Cookie(JSESSIONID, sessionId));

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(@MemberAuth Long memberId, @RequestBody PasswordChangeRequest request) {
        memberService.changePassword(memberId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
