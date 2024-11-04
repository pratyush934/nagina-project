package com.userservice.userservice.controller;

import com.userservice.userservice.payload.AuthenticationRequest;
import com.userservice.userservice.payload.AuthenticationResponse;
import com.userservice.userservice.payload.RegisterRequest;
import com.userservice.userservice.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AUTH;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        if(registerRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AuthenticationResponse register = authenticationService.register(registerRequest);
        return new ResponseEntity<AuthenticationResponse>(register, HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        if(authenticationRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AuthenticationResponse authenticate = authenticationService.authenticate(authenticationRequest);
        return new ResponseEntity<>(authenticate, HttpStatus.OK);
    }
}
