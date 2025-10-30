package com.api.ecomerce.services;

import java.util.List;

import com.api.ecomerce.dtos.request.CreateBrandRequest;
import com.api.ecomerce.dtos.request.UpdateBrandRequest;
import com.api.ecomerce.dtos.response.BrandResponse;

public interface BrandService {

    BrandResponse createBrand(CreateBrandRequest request);

    List<BrandResponse> getAllBrands();

    BrandResponse getBrandById(Long id);

    BrandResponse updateBrand(Long id, UpdateBrandRequest request);

    void deleteBrand(Long id);
}

