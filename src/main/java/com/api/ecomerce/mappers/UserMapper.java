package com.api.ecomerce.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.api.ecomerce.dtos.request.UpdateProfileRequest;
import com.api.ecomerce.dtos.response.ProfileResponse;
import com.api.ecomerce.models.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roleName", source = "role.name")
    @Mapping(target = "isAdmin", expression = "java(user.getRole().getName().equals(\"ADMIN\"))")
    @Mapping(target = "authenticated", constant = "true")
    ProfileResponse toProfileResponse(User user);

    @BeanMapping(ignoreByDefault = true)
    void updateUserFromRequest(@MappingTarget User user, UpdateProfileRequest request);

    default void safeUpdateUserFromRequest(User user, UpdateProfileRequest request) {
        if (request != null && user != null) {
            updateUserFromRequest(user, request);
        }
    }
}
