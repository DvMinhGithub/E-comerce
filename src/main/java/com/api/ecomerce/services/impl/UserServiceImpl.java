package com.api.ecomerce.services.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.ecomerce.dtos.request.UpdateProfileRequest;
import com.api.ecomerce.dtos.response.ProfileResponse;
import com.api.ecomerce.exceptions.factory.ExceptionFactory;
import com.api.ecomerce.mappers.UserMapper;
import com.api.ecomerce.models.User;
import com.api.ecomerce.repositories.UserRepository;
import com.api.ecomerce.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ProfileResponse getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> ExceptionFactory.resourceNotFound("User", "id", userId));
        return userMapper.toProfileResponse(user);
    }

    @Override
    public ProfileResponse updateUserProfile(UpdateProfileRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();
        User existingUser = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> ExceptionFactory.resourceNotFound("User", "email", request.getEmail()));
        if (existingUser.getId() != userId) {
            throw ExceptionFactory.conflict("User", "email", request.getEmail());
        }
        userMapper.updateUserFromRequest(existingUser, request);
        User savedUser = userRepository.save(existingUser);
        return userMapper.toProfileResponse(savedUser);
    }
}
