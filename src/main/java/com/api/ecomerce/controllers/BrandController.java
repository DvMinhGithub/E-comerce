package com.api.ecomerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecomerce.dtos.request.CreateBrandRequest;
import com.api.ecomerce.dtos.request.UpdateBrandRequest;
import com.api.ecomerce.dtos.response.ApiResponse;
import com.api.ecomerce.dtos.response.BrandResponse;
import com.api.ecomerce.services.BrandService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(@Valid @RequestBody CreateBrandRequest request) {
        BrandResponse brand = brandService.createBrand(request);
        ApiResponse<BrandResponse> response =
                ApiResponse.create(HttpStatus.CREATED.value(), "Brand created successfully", brand);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllBrands() {
        List<BrandResponse> brands = brandService.getAllBrands();
        ApiResponse<List<BrandResponse>> response =
                ApiResponse.create(HttpStatus.OK.value(), "Brands fetched successfully", brands);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandById(@PathVariable Long id) {
        BrandResponse brand = brandService.getBrandById(id);
        ApiResponse<BrandResponse> response =
                ApiResponse.create(HttpStatus.OK.value(), "Brand fetched successfully", brand);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand(
            @PathVariable Long id, @Valid @RequestBody UpdateBrandRequest request) {
        BrandResponse brand = brandService.updateBrand(id, request);
        ApiResponse<BrandResponse> response =
                ApiResponse.create(HttpStatus.OK.value(), "Brand updated successfully", brand);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        ApiResponse<String> response = ApiResponse.create(HttpStatus.OK.value(), "Brand deleted successfully");
        return ResponseEntity.ok(response);
    }
}
