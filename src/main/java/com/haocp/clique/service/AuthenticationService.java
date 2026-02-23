package com.haocp.clique.service;

import com.haocp.clique.dto.request.auth.LoginRequest;
import com.haocp.clique.dto.request.auth.RegisterRequest;
import com.haocp.clique.dto.response.auth.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(LoginRequest request);
    AuthenticationResponse register(RegisterRequest request);
}
