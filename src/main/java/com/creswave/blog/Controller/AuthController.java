package com.creswave.blog.Controller;

import com.creswave.blog.DTO.AuthRequest;
import com.creswave.blog.DTO.AuthResponse;
import com.creswave.blog.DTO.RegisterRequest;
import com.creswave.blog.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }
}
