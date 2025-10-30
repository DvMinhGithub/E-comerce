package com.api.ecomerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.ecomerce.dtos.request.CreateColorRequest;
import com.api.ecomerce.dtos.response.ColorResponse;
import com.api.ecomerce.models.Color;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Color toEntity(CreateColorRequest request);

    ColorResponse toResponse(Color color);
}
