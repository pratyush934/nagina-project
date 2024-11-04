package com.userservice.userservice.services;

import com.userservice.userservice.payload.AuthenticationRequest;
import com.userservice.userservice.payload.AuthenticationResponse;
import com.userservice.userservice.payload.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest registerRequest);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

}
