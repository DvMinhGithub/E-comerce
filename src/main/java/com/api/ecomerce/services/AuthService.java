package com.api.ecomerce.services;

import com.api.ecomerce.dtos.request.LoginRequest;
import com.api.ecomerce.dtos.request.RegisterRequest;
import com.api.ecomerce.dtos.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
