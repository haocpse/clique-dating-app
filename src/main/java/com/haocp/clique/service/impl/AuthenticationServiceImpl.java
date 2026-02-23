package com.haocp.clique.service.impl;

import com.haocp.clique.dto.request.auth.LoginRequest;
import com.haocp.clique.dto.request.auth.RegisterRequest;
import com.haocp.clique.dto.response.auth.AuthenticationResponse;
import com.haocp.clique.entity.User;
import com.haocp.clique.exception.AppException;
import com.haocp.clique.exception.ErrorCode;
import com.haocp.clique.repository.UserRepository;
import com.haocp.clique.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> AppException.builder()
                        .errorCode(ErrorCode.INVALID_CREDENTIALS)
                        .build());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw AppException.builder()
                    .errorCode(ErrorCode.INVALID_CREDENTIALS)
                    .build();
        }
        if (!user.getEnabled()) {
            throw AppException.builder()
                    .errorCode(ErrorCode.USER_DISABLED)
                    .build();
        }
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw AppException.builder()
                    .errorCode(ErrorCode.PASSWORD_MISMATCH)
                    .build();
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw AppException.builder()
                    .errorCode(ErrorCode.EMAIL_ALREADY_EXISTS)
                    .build();
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();
        userRepository.save(user);
        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("haocp.cliquedating")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(7776000, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("userId", user.getId())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException("Cannot create token", e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (user.getRole() != null) {
            stringJoiner.add("ROLE_" + user.getRole());
        }
        return stringJoiner.toString();
    }
}
