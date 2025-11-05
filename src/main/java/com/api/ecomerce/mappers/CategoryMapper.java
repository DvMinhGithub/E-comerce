package com.api.ecomerce.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.api.ecomerce.dtos.request.CreateCategoryRequest;
import com.api.ecomerce.dtos.request.UpdateCategoryRequest;
import com.api.ecomerce.dtos.response.CategoryResponse;
import com.api.ecomerce.models.Category;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    @BeanMapping(ignoreByDefault = true)
    Category toEntity(CreateCategoryRequest request);

    @BeanMapping(ignoreByDefault = true)
    void updateEntityFromRequest(@MappingTarget Category category, UpdateCategoryRequest request);

    default void safeUpdateEntityFromRequest(Category category, UpdateCategoryRequest request) {
        if (request != null && category != null) {
            updateEntityFromRequest(category, request);
        }
    }
}
