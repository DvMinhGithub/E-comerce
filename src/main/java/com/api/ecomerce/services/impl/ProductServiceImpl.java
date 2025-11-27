package com.api.ecomerce.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.api.ecomerce.dtos.request.CreateProductRequest;
import com.api.ecomerce.dtos.request.ProductQueryRequest;
import com.api.ecomerce.dtos.request.UpdateProductRequest;
import com.api.ecomerce.dtos.response.PagedResponse;
import com.api.ecomerce.dtos.response.ProductResponse;
import com.api.ecomerce.exceptions.ExceptionFactory;
import com.api.ecomerce.mappers.ProductMapper;
import com.api.ecomerce.models.Color;
import com.api.ecomerce.models.Product;
import com.api.ecomerce.models.ProductImage;
import com.api.ecomerce.models.ProductSize;
import com.api.ecomerce.models.User;
import com.api.ecomerce.repositories.BrandRepository;
import com.api.ecomerce.repositories.CategoryRepository;
import com.api.ecomerce.repositories.ColorRepository;
import com.api.ecomerce.repositories.ProductRepository;
import com.api.ecomerce.services.ProductService;
import com.api.ecomerce.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        var brand = brandRepository
                .findById(request.getBrandId())
                .orElseThrow(() -> ExceptionFactory.resourceNotFound("Brand", "id", request.getBrandId()));

        if (productRepository.existsByName(request.getName())) {
            throw ExceptionFactory.conflict("Product", "name", request.getName());
        }

        var category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> ExceptionFactory.resourceNotFound("Category", "id", request.getCategoryId()));

        List<Color> colors = fetchColors(request.getColorIds());

        Set<ProductSize> sizes = parseSizes(request.getSizes());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .brand(brand)
                .category(category)
                .price(request.getPrice())
                .totalQty(request.getTotalQty())
                .user(currentUser)
                .sizes(sizes)
                .colors(new ArrayList<>(colors))
                .build();

        updateProductImages(product, request.getImageUrls());

        Product saved = productRepository.save(product);

        return productMapper.toResponse(saved);
    }

    @Override
    public PagedResponse<ProductResponse> getProducts(ProductQueryRequest request) {
        Pageable pageable = PaginationUtils.build(request, "createdAt");
        Specification<Product> specification = com.api.ecomerce.specifications.ProductSpecifications.build(request);

        Page<Product> productPage = productRepository.findAll(specification, pageable);

        List<ProductResponse> items =
                productPage.getContent().stream().map(productMapper::toResponse).collect(Collectors.toList());

        return PagedResponse.<ProductResponse>builder()
                .content(items)
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = getProductByIdOrThrow(id);

        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = getProductByIdOrThrow(id);

        if (StringUtils.hasText(request.getName())) {
            product.setName(request.getName());
        }
        if (StringUtils.hasText(request.getDescription())) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getTotalQty() != null) {
            product.setTotalQty(request.getTotalQty());
        }

        if (request.getBrandId() != null) {
            var brand = brandRepository
                    .findById(request.getBrandId())
                    .orElseThrow(() -> ExceptionFactory.resourceNotFound("Brand", "id", request.getBrandId()));
            product.setBrand(brand);
        }

        if (request.getCategoryId() != null) {
            var category = categoryRepository
                    .findById(request.getCategoryId())
                    .orElseThrow(() -> ExceptionFactory.resourceNotFound("Category", "id", request.getCategoryId()));
            product.setCategory(category);
        }

        if (request.getSizes() != null && !request.getSizes().isEmpty()) {
            product.setSizes(parseSizes(request.getSizes()));
        }

        if (request.getColorIds() != null && !request.getColorIds().isEmpty()) {
            product.setColors(fetchColors(request.getColorIds()));
        }

        if (request.getImageUrls() != null) {
            updateProductImages(product, request.getImageUrls());
        }

        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw ExceptionFactory.resourceNotFound("Product", "id", id);
        }
        productRepository.deleteById(id);
    }

    private Product getProductByIdOrThrow(Long id) {
        return productRepository.findById(id).orElseThrow(() -> ExceptionFactory.resourceNotFound("Product", "id", id));
    }

    private Set<ProductSize> parseSizes(Set<String> sizes) {
        Set<ProductSize> result = new HashSet<>();
        try {
            for (String s : sizes) {
                result.add(ProductSize.valueOf(s));
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid size provided. Allowed sizes: S,M,L,XL,XXL");
        }
        return result;
    }

    private List<Color> fetchColors(Set<Long> colorIds) {
        List<Color> colors = colorRepository.findAllById(colorIds);
        if (colors.size() != colorIds.size()) {
            List<Long> foundIds = colors.stream().map(Color::getId).collect(Collectors.toList());
            Long missing = colorIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .findFirst()
                    .orElse(null);
            throw ExceptionFactory.resourceNotFound("Color", "id", missing);
        }
        return colors;
    }

    private void updateProductImages(Product product, List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return;
        }
        // If updating, we might want to clear existing images first if that's the logic
        // In createProduct, images are empty. In updateProduct, we cleared them.
        // Let's assume we clear them if the list is provided (which is what update
        // logic did).
        // But for createProduct, it's fine too.
        if (product.getImages() != null) {
            product.getImages().clear();
        } else {
            // Should not happen if initialized in entity, but good to be safe or just rely
            // on entity init
        }

        int order = 0;
        for (String url : imageUrls) {
            ProductImage img = ProductImage.builder()
                    .imageUrl(url)
                    .imageOrder(order++)
                    .product(product)
                    .build();
            product.getImages().add(img);
        }
    }
}
