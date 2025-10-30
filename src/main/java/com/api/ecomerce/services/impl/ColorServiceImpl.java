package com.api.ecomerce.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.ecomerce.dtos.request.CreateColorRequest;
import com.api.ecomerce.dtos.request.UpdateColorRequest;
import com.api.ecomerce.dtos.response.ColorResponse;
import com.api.ecomerce.exceptions.ExceptionFactory;
import com.api.ecomerce.mappers.ColorMapper;
import com.api.ecomerce.models.Color;
import com.api.ecomerce.repositories.ColorRepository;
import com.api.ecomerce.services.ColorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public ColorResponse createColor(CreateColorRequest request) {
        String normalizedName = request.getName().toLowerCase();

        if (colorRepository.existsByName(normalizedName)) {
            throw ExceptionFactory.conflict("Color", "name", request.getName());
        }

        Color color = colorMapper.toEntity(request);
        Color savedColor = colorRepository.save(color);
        return colorMapper.toResponse(savedColor);
    }

    @Override
    public List<ColorResponse> getAllColors() {
        List<Color> colors = colorRepository.findByIsActiveTrueOrderByNameAsc();
        return colors.stream().map(colorMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ColorResponse getColorById(Long id) {
        Color color =
                colorRepository.findById(id).orElseThrow(() -> ExceptionFactory.resourceNotFound("Color", "id", id));
        return colorMapper.toResponse(color);
    }

    @Override
    public ColorResponse updateColor(Long id, UpdateColorRequest request) {
        Color color =
                colorRepository.findById(id).orElseThrow(() -> ExceptionFactory.resourceNotFound("Color", "id", id));

        String normalizedName = request.getName().toLowerCase();

        if (colorRepository.existsByNameAndIdNot(normalizedName, id)) {
            throw ExceptionFactory.conflict("Color", "name", request.getName());
        }

        color.setName(normalizedName);
        Color updatedColor = colorRepository.save(color);
        return colorMapper.toResponse(updatedColor);
    }

    @Override
    public void deleteColor(Long id) {
        Color color =
                colorRepository.findById(id).orElseThrow(() -> ExceptionFactory.resourceNotFound("Color", "id", id));

        colorRepository.delete(color);
    }
}
