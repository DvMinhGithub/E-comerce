package com.api.ecomerce.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.api.ecomerce.dtos.request.CreateColorRequest;
import com.api.ecomerce.dtos.request.UpdateColorRequest;
import com.api.ecomerce.dtos.response.ColorResponse;
import com.api.ecomerce.models.Color;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ColorMapper {

    @BeanMapping(ignoreByDefault = true)
    Color toEntity(CreateColorRequest request);

    ColorResponse toResponse(Color color);

    void updateEntityFromRequest(@MappingTarget Color color, UpdateColorRequest request);
}
