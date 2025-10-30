package com.api.ecomerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecomerce.dtos.request.CreateColorRequest;
import com.api.ecomerce.dtos.request.UpdateColorRequest;
import com.api.ecomerce.dtos.response.ApiResponse;
import com.api.ecomerce.dtos.response.ColorResponse;
import com.api.ecomerce.services.ColorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/colors")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @PostMapping
    public ResponseEntity<ApiResponse<ColorResponse>> createColor(@Valid @RequestBody CreateColorRequest request) {
        ColorResponse color = colorService.createColor(request);
        ApiResponse<ColorResponse> response =
                ApiResponse.create(HttpStatus.CREATED.value(), "Color created successfully", color);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ColorResponse>>> getAllColors() {
        List<ColorResponse> colors = colorService.getAllColors();
        ApiResponse<List<ColorResponse>> response =
                ApiResponse.create(HttpStatus.OK.value(), "Colors fetched successfully", colors);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ColorResponse>> getColorById(@PathVariable Long id) {
        ColorResponse color = colorService.getColorById(id);
        ApiResponse<ColorResponse> response =
                ApiResponse.create(HttpStatus.OK.value(), "Color fetched successfully", color);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ColorResponse>> updateColor(
            @PathVariable Long id, @Valid @RequestBody UpdateColorRequest request) {
        ColorResponse color = colorService.updateColor(id, request);
        ApiResponse<ColorResponse> response =
                ApiResponse.create(HttpStatus.OK.value(), "Color updated successfully", color);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteColor(@PathVariable Long id) {
        colorService.deleteColor(id);
        ApiResponse<String> response = ApiResponse.create(HttpStatus.OK.value(), "Color deleted successfully");
        return ResponseEntity.ok(response);
    }
}
