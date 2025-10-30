package com.api.ecomerce.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.ecomerce.dtos.request.LoginRequest;
import com.api.ecomerce.dtos.request.RegisterRequest;
import com.api.ecomerce.dtos.response.AuthResponse;
import com.api.ecomerce.exceptions.ExceptionFactory;
import com.api.ecomerce.models.Role;
import com.api.ecomerce.models.RoleType;
import com.api.ecomerce.models.User;
import com.api.ecomerce.repositories.UserRepository;
import com.api.ecomerce.services.AuthService;
import com.api.ecomerce.services.JwtService;
import com.api.ecomerce.services.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw ExceptionFactory.conflict("User", "email", request.getEmail());
        }

        Role userRole = roleService.getRoleByName(RoleType.USER);

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw ExceptionFactory.invalidCredentials();
        }

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> ExceptionFactory.resourceNotFound("User", "email", request.getEmail()));

        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}
