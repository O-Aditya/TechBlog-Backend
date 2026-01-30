package com.adityacode.Blog_App.controllers;


import com.adityacode.Blog_App.domain.dtos.AuthResponse;
import com.adityacode.Blog_App.domain.dtos.LoginRequest;
import com.adityacode.Blog_App.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping()
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        //T0 Validate User
        UserDetails user = authenticationService
                .authenticate(
                        loginRequest.getEmail(),
                        loginRequest.getPassword());
        //TO generate JWT Token
        String token = authenticationService.generateToken(user);

        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .expires_in(86400)
                .build();

        return  ResponseEntity.ok(authResponse);
    }
}
