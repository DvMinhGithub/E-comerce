package com.api.ecomerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecomerce.dtos.request.CreateProductRequest;
import com.api.ecomerce.dtos.request.ProductQueryRequest;
import com.api.ecomerce.dtos.response.ApiResponse;
import com.api.ecomerce.dtos.response.PagedResponse;
import com.api.ecomerce.dtos.response.ProductResponse;
import com.api.ecomerce.services.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody CreateProductRequest request) {
        ProductResponse product = productService.createProduct(request);
        ApiResponse<ProductResponse> response =
                ApiResponse.create(HttpStatus.CREATED.value(), "Product created successfully", product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> getProducts(
            @ModelAttribute ProductQueryRequest request) {
        PagedResponse<ProductResponse> products = productService.getProducts(request);
        ApiResponse<PagedResponse<ProductResponse>> response =
                ApiResponse.create(HttpStatus.OK.value(), "Products fetched successfully", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        ApiResponse<ProductResponse> response =
                ApiResponse.create(HttpStatus.OK.value(), "Product fetched successfully", product);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id, @Valid @RequestBody com.api.ecomerce.dtos.request.UpdateProductRequest request) {
        ProductResponse product = productService.updateProduct(id, request);
        ApiResponse<ProductResponse> response =
                ApiResponse.create(HttpStatus.OK.value(), "Product updated successfully", product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        ApiResponse<Void> response = ApiResponse.create(HttpStatus.OK.value(), "Product deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
