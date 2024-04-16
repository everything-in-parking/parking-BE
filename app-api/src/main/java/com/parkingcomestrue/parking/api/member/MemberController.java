package com.parkingcomestrue.parking.api.member;

import com.parkingcomestrue.parking.application.auth.AuthService;
import com.parkingcomestrue.parking.application.member.MemberService;
import com.parkingcomestrue.parking.application.member.dto.MemberLoginRequest;
import com.parkingcomestrue.parking.application.member.dto.MemberSignupRequest;
import com.parkingcomestrue.parking.application.member.dto.PasswordChangeRequest;
import com.parkingcomestrue.parking.config.argumentresolver.MemberAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 컨트롤러")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private static final String JSESSIONID = "JSESSIONID";

    private final MemberService memberService;
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody MemberSignupRequest request) {
        memberService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<Void> signIn(HttpServletResponse httpServletResponse,
                                       @RequestBody MemberLoginRequest request) {
        Long memberId = memberService.login(request);
        String sessionId = authService.createSession(memberId);
        httpServletResponse.addCookie(new Cookie(JSESSIONID, sessionId));

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경")
    @PatchMapping("/member/password")
    public ResponseEntity<Void> changePassword(@Parameter(hidden = true) @MemberAuth Long memberId,
                                               @RequestBody PasswordChangeRequest request) {
        memberService.changePassword(memberId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
