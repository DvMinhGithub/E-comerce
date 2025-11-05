package com.api.ecomerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.api.ecomerce.dtos.response.ProductImageResponse;
import com.api.ecomerce.models.ProductImage;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductImageMapper {
    ProductImageResponse toResponse(ProductImage productImage);
}
