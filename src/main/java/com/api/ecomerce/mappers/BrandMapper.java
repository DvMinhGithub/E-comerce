package com.api.ecomerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.ecomerce.dtos.request.CreateBrandRequest;
import com.api.ecomerce.dtos.response.BrandResponse;
import com.api.ecomerce.models.Brand;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Brand toEntity(CreateBrandRequest request);

    BrandResponse toResponse(Brand brand);
}

