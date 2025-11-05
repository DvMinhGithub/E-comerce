package com.api.ecomerce.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.ecomerce.dtos.request.CreateCategoryRequest;
import com.api.ecomerce.dtos.request.UpdateCategoryRequest;
import com.api.ecomerce.dtos.response.CategoryResponse;
import com.api.ecomerce.exceptions.ExceptionFactory;
import com.api.ecomerce.mappers.CategoryMapper;
import com.api.ecomerce.models.Category;
import com.api.ecomerce.repositories.CategoryRepository;
import com.api.ecomerce.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw ExceptionFactory.conflict("Category", "name", request.getName());
        }

        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findByIsActiveTrueOrderByNameAsc();
        return categories.stream().map(categoryMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> ExceptionFactory.resourceNotFound("Category", "id", id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> ExceptionFactory.resourceNotFound("Category", "id", id));

        if (categoryRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw ExceptionFactory.conflict("Category", "name", request.getName());
        }

        categoryMapper.updateEntityFromRequest(category, request);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> ExceptionFactory.resourceNotFound("Category", "id", id));

        categoryRepository.delete(category);
    }
}
