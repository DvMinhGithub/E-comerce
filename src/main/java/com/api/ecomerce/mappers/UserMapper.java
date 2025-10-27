package com.api.ecomerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.api.ecomerce.dtos.request.UpdateProfileRequest;
import com.api.ecomerce.dtos.response.ProfileResponse;
import com.api.ecomerce.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roleName", source = "role.name")
    @Mapping(target = "isAdmin", expression = "java(user.getRole().getName().equals(\"ADMIN\"))")
    @Mapping(target = "authenticated", constant = "true")
    ProfileResponse toProfileResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "isAdmin", ignore = true)
    @Mapping(target = "authenticated", ignore = true)
    @Mapping(target = "avatarId", ignore = true)
    @Mapping(target = "dob", ignore = true)
    User toUser(UpdateProfileRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "isAdmin", ignore = true)
    @Mapping(target = "authenticated", ignore = true)
    @Mapping(target = "avatarId", ignore = true)
    @Mapping(target = "dob", ignore = true)
    void updateUserFromRequest(@MappingTarget User user, UpdateProfileRequest request);
}
