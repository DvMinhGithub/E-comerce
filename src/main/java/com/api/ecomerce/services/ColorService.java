package com.api.ecomerce.services;

import java.util.List;

import com.api.ecomerce.dtos.request.CreateColorRequest;
import com.api.ecomerce.dtos.request.UpdateColorRequest;
import com.api.ecomerce.dtos.response.ColorResponse;

public interface ColorService {

    ColorResponse createColor(CreateColorRequest request);

    List<ColorResponse> getAllColors();

    ColorResponse getColorById(Long id);

    ColorResponse updateColor(Long id, UpdateColorRequest request);

    void deleteColor(Long id);
}
