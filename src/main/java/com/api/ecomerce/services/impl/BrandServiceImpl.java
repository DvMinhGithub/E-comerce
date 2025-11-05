package com.api.ecomerce.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.ecomerce.dtos.request.CreateBrandRequest;
import com.api.ecomerce.dtos.request.UpdateBrandRequest;
import com.api.ecomerce.dtos.response.BrandResponse;
import com.api.ecomerce.exceptions.ExceptionFactory;
import com.api.ecomerce.mappers.BrandMapper;
import com.api.ecomerce.models.Brand;
import com.api.ecomerce.repositories.BrandRepository;
import com.api.ecomerce.services.BrandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponse createBrand(CreateBrandRequest request) {
        if (brandRepository.existsByName(request.getName())) {
            throw ExceptionFactory.conflict("Brand", "name", request.getName());
        }

        Brand savedBrand = brandRepository.save(brandMapper.toEntity(request));
        return brandMapper.toResponse(savedBrand);
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        List<Brand> brands = brandRepository.findByIsActiveTrueOrderByNameAsc();
        return brands.stream().map(brandMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public BrandResponse getBrandById(Long id) {
        Brand brand =
                brandRepository.findById(id).orElseThrow(() -> ExceptionFactory.resourceNotFound("Brand", "id", id));
        return brandMapper.toResponse(brand);
    }

    @Override
    public BrandResponse updateBrand(Long id, UpdateBrandRequest request) {
        Brand brand =
                brandRepository.findById(id).orElseThrow(() -> ExceptionFactory.resourceNotFound("Brand", "id", id));

        if (brandRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw ExceptionFactory.conflict("Brand", "name", request.getName());
        }

        brandMapper.updateEntityFromRequest(brand, request);

        Brand updatedBrand = brandRepository.save(brand);
        return brandMapper.toResponse(updatedBrand);
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brand =
                brandRepository.findById(id).orElseThrow(() -> ExceptionFactory.resourceNotFound("Brand", "id", id));

        brandRepository.delete(brand);
    }
}
