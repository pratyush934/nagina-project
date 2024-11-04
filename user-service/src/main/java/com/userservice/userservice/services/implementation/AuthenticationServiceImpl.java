package com.userservice.userservice.services.implementation;

import com.userservice.userservice.config.JwtService;
import com.userservice.userservice.model.Role;
import com.userservice.userservice.model.User;
import com.userservice.userservice.payload.AuthenticationRequest;
import com.userservice.userservice.payload.AuthenticationResponse;
import com.userservice.userservice.payload.RegisterRequest;
import com.userservice.userservice.repository.RoleRepository;
import com.userservice.userservice.repository.UserRepository;
import com.userservice.userservice.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        Set<Role> roles = new HashSet<>();

        for (Role role : registerRequest.getRoles()) {
            Role saveRole = roleRepository
                    .findByRoleName(role.getRoleName())
                    .orElseGet(() -> roleRepository.save(role));
            roles.add(saveRole);
        }


        User user = User
                .builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .passWord(passwordEncoder.encode(registerRequest.getPassWord()))
                .roles(roles)
                .address(registerRequest.getAddress())
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .address(registerRequest.getAddress())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            logger.warn("Sorry but there is an error in authenticate method of AuthenticationServiceImplementation {}", e.getMessage());
        }

        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new NoSuchElementException("Nahi Mila Nahi Mila so sad from authenticate/AuthenticationServiceImp {}"));
        String token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .build();
    }
}
