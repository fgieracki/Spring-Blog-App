package com.fgieracki.blogapi.controller;

import com.fgieracki.blogapi.payload.JwtAuthResponse;
import com.fgieracki.blogapi.payload.LoginDTO;
import com.fgieracki.blogapi.payload.RegisterDTO;
import com.fgieracki.blogapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping({"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO loginDTO){
        String token = authService.login(loginDTO);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping({"/register", "/signup"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDTO){
        String response = authService.register(registerDTO);
        return ResponseEntity.ok(response);
    }
}
