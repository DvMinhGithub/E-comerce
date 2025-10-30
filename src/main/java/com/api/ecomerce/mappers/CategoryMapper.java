package com.api.ecomerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.ecomerce.dtos.request.CreateCategoryRequest;
import com.api.ecomerce.dtos.request.UpdateCategoryRequest;
import com.api.ecomerce.dtos.response.CategoryResponse;
import com.api.ecomerce.models.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity(CreateCategoryRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity(UpdateCategoryRequest request);

    CategoryResponse toResponse(Category category);
}
