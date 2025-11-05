package com.api.ecomerce.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private String brand;
    private CategoryResponse category;
    private Set<String> sizes;
    private Set<ColorResponse> colors;
    private BigDecimal price;
    private Integer totalQty;
    private Integer totalSold;
    private Integer qtyLeft;
    private Set<ProductImageResponse> images;
    private Long userId;
    private Integer totalReviews;
    private Double averageRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
