package com.api.ecomerce.dtos.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQueryRequest implements PageableRequest {

    private String name;
    private Long brandId;
    private Long categoryId;
    private Long colorId;
    private String size;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer page = 0;
    private Integer pageSize = 10;
    private String sortBy = "createdAt";
    private String sortDir = "desc";
}
