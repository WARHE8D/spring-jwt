package com.warhead.security.authController;

import com.warhead.security.requestbody.AuthResponse;
import com.warhead.security.requestbody.AuthenticationReqBody;
import com.warhead.security.requestbody.RegisterReqBody;
import com.warhead.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse>register(@RequestBody RegisterReqBody request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthResponse>authenticate(@RequestBody AuthenticationReqBody request){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
