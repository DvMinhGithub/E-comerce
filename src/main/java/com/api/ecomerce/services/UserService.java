package com.api.ecomerce.services;

import com.api.ecomerce.dtos.request.UpdateProfileRequest;
import com.api.ecomerce.dtos.response.ProfileResponse;

public interface UserService {

    ProfileResponse getUserProfile();

    ProfileResponse updateUserProfile(UpdateProfileRequest request);
}
