package com.haocp.clique.service;

import com.haocp.clique.dto.request.LoginRequest;
import com.haocp.clique.dto.request.RegisterRequest;
import com.haocp.clique.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(LoginRequest request);

    AuthenticationResponse register(RegisterRequest request);
}
