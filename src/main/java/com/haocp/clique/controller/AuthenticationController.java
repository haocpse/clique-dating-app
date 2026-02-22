package com.haocp.clique.controller;

import com.haocp.clique.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    final AuthenticationService authenticationService;

}
