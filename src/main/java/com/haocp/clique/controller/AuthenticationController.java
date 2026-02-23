package com.haocp.clique.controller;

import com.haocp.clique.dto.ApiResponse;
import com.haocp.clique.dto.request.auth.LoginRequest;
import com.haocp.clique.dto.request.auth.RegisterRequest;
import com.haocp.clique.dto.response.auth.AuthenticationResponse;
import com.haocp.clique.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authenticationService.login(request))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .code(201)
                .message("Registration successful")
                .data(authenticationService.register(request))
                .build();
    }
}
