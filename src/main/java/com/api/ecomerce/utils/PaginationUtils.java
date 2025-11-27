package com.api.ecomerce.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.api.ecomerce.dtos.request.PageableRequest;

public final class PaginationUtils {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 50;
    private static final String DEFAULT_SORT = "createdAt";

    private PaginationUtils() {}

    public static Pageable build(PageableRequest request, String fallbackSortBy) {
        int page = DEFAULT_PAGE;
        int size = DEFAULT_SIZE;
        String sortBy = resolveSortBy(request, fallbackSortBy);
        Sort.Direction direction = Sort.Direction.DESC;

        if (request != null) {
            if (request.getPage() != null && request.getPage() >= 0) {
                page = request.getPage();
            }

            if (request.getPageSize() != null && request.getPageSize() > 0) {
                size = Math.min(request.getPageSize(), MAX_SIZE);
            }

            if (StringUtils.hasText(request.getSortDir())) {
                direction = Sort.Direction.fromOptionalString(
                                request.getSortDir().toUpperCase())
                        .orElse(Sort.Direction.DESC);
            }
        }

        return PageRequest.of(page, size, Sort.by(direction, sortBy));
    }

    private static String resolveSortBy(PageableRequest request, String fallbackSort) {
        if (request != null && StringUtils.hasText(request.getSortBy())) {
            return request.getSortBy();
        }
        if (StringUtils.hasText(fallbackSort)) {
            return fallbackSort;
        }
        return DEFAULT_SORT;
    }
}
