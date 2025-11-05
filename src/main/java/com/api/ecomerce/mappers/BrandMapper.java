package com.api.ecomerce.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.api.ecomerce.dtos.request.CreateBrandRequest;
import com.api.ecomerce.dtos.request.UpdateBrandRequest;
import com.api.ecomerce.dtos.response.BrandResponse;
import com.api.ecomerce.models.Brand;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @BeanMapping(ignoreByDefault = true)
    Brand toEntity(CreateBrandRequest request);

    BrandResponse toResponse(Brand brand);

    void updateEntityFromRequest(@MappingTarget Brand brand, UpdateBrandRequest request);
}
