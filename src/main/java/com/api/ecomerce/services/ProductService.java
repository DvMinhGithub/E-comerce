package com.api.ecomerce.services;

import com.api.ecomerce.dtos.request.CreateProductRequest;
import com.api.ecomerce.dtos.request.ProductQueryRequest;
import com.api.ecomerce.dtos.request.UpdateProductRequest;
import com.api.ecomerce.dtos.response.PagedResponse;
import com.api.ecomerce.dtos.response.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    PagedResponse<ProductResponse> getProducts(ProductQueryRequest request);

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, UpdateProductRequest request);

    void deleteProduct(Long id);
}
