package com.api.ecomerce.services;

import java.util.List;

import com.api.ecomerce.dtos.request.CreateCategoryRequest;
import com.api.ecomerce.dtos.request.UpdateCategoryRequest;
import com.api.ecomerce.dtos.response.CategoryResponse;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, UpdateCategoryRequest request);

    void deleteCategory(Long id);
}
