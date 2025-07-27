package com.fiap.challenge.parts.dto;

import java.math.BigDecimal;

public record UpdatePartRequestDTO(
    String name,
    String description,
    BigDecimal price,
    Integer stockQuantity,
    Integer minimumStock
) {}