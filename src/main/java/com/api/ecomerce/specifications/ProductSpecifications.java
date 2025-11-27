package com.api.ecomerce.specifications;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.api.ecomerce.dtos.request.ProductQueryRequest;
import com.api.ecomerce.models.Color;
import com.api.ecomerce.models.Product;
import com.api.ecomerce.models.ProductSize;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class ProductSpecifications {

    public static Specification<Product> build(ProductQueryRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(request.getName())) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }

            if (request.getBrandId() != null) {
                predicates.add(cb.equal(root.get("brand").get("id"), request.getBrandId()));
            }

            if (request.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), request.getCategoryId()));
            }

            if (request.getColorId() != null) {
                Join<Product, Color> colorJoin = root.join("colors");
                predicates.add(cb.equal(colorJoin.get("id"), request.getColorId()));
                query.distinct(true);
            }

            if (StringUtils.hasText(request.getSize())) {
                try {
                    ProductSize size =
                            ProductSize.valueOf(request.getSize().trim().toUpperCase());
                    Join<Product, ProductSize> sizeJoin = root.join("sizes");
                    predicates.add(cb.equal(sizeJoin, size));
                    query.distinct(true);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid size filter. Allowed sizes: S,M,L,XL,XXL");
                }
            }

            if (request.getMinPrice() != null && request.getMaxPrice() != null) {
                BigDecimal minPrice = request.getMinPrice();
                BigDecimal maxPrice = request.getMaxPrice();
                if (minPrice.compareTo(maxPrice) > 0) {
                    BigDecimal temp = minPrice;
                    minPrice = maxPrice;
                    maxPrice = temp;
                }
                predicates.add(cb.between(root.get("price"), minPrice, maxPrice));
            } else if (request.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
            } else if (request.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), request.getMaxPrice()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
