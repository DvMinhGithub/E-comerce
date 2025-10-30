package com.api.ecomerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.ecomerce.models.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {

    Optional<Color> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    List<Color> findByIsActiveTrueOrderByNameAsc();
}
