package com.api.ecomerce.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.api.ecomerce.dtos.request.CreateProductRequest;
import com.api.ecomerce.dtos.request.UpdateProductRequest;
import com.api.ecomerce.dtos.response.ProductResponse;
import com.api.ecomerce.models.Product;
import com.api.ecomerce.models.Review;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { CategoryMapper.class,
        ColorMapper.class, ProductImageMapper.class })
public interface ProductMapper {

    @Mapping(target = "userId", source = "user.id")
    ProductResponse toResponse(Product product);

    @AfterMapping
    default void afterToResponse(@MappingTarget ProductResponse response, Product product) {
        if (product != null) {
            response.setQtyLeft(product.getTotalQty() - product.getTotalSold());
            if (product.getReviews() != null) {
                response.setTotalReviews(product.getReviews().size());
                response.setAverageRating(
                        product.getReviews().stream().mapToInt(Review::getRating).average().orElse(0.0));
            } else {
                response.setTotalReviews(0);
                response.setAverageRating(0.0);
            }
        }
    }

    Product toEntity(CreateProductRequest request);

    void updateEntityFromRequest(@MappingTarget Product product, UpdateProductRequest request);
}

