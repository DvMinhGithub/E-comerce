package com.api.ecomerce.dtos.request;

public interface PageableRequest {
    Integer getPage();

    Integer getPageSize();

    String getSortBy();

    String getSortDir();
}
