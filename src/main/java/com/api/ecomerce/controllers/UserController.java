package com.api.ecomerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecomerce.dtos.request.UpdateProfileRequest;
import com.api.ecomerce.dtos.response.ApiResponse;
import com.api.ecomerce.dtos.response.ProfileResponse;
import com.api.ecomerce.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getUserProfile(Authentication authentication) {
        ProfileResponse profile = userService.getUserProfile();
        return ResponseEntity.ok(ApiResponse.create(HttpStatus.OK.value(), "Profile retrieved successfully", profile));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateUserProfile(
            Authentication authentication, @Valid @RequestBody UpdateProfileRequest request) {
        ProfileResponse updatedProfile = userService.updateUserProfile(request);
        return ResponseEntity.ok(
                ApiResponse.create(HttpStatus.OK.value(), "Profile updated successfully", updatedProfile));
    }
}
