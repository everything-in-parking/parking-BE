package com.parkingcomestrue.parking.api.auth.authcode;

import com.parkingcomestrue.parking.application.auth.AuthService;
import com.parkingcomestrue.parking.application.auth.authcode.dto.AuthCodeCertificateRequest;
import com.parkingcomestrue.parking.application.auth.authcode.dto.AuthCodeRequest;
import com.parkingcomestrue.parking.application.auth.authcode.dto.AuthCodeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증코드 컨트롤러")
@RestController
@RequiredArgsConstructor
public class AuthCodeController {

    private final AuthService authService;

    @PostMapping("/authcode")
    public ResponseEntity<AuthCodeResponse> createAuthCode(@RequestBody AuthCodeRequest authCodeRequest) {
        String authCode = authService.createAuthCode(authCodeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthCodeResponse(authCode));
    }

    @DeleteMapping("/authcode")
    public ResponseEntity<Void> certificateAuthCode(
            @RequestBody AuthCodeCertificateRequest authCodeCertificateRequest) {
        authService.certificateAuthCode(authCodeCertificateRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
