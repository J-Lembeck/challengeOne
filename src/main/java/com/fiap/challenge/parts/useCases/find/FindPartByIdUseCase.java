package com.fiap.challenge.parts.useCases.find;

import java.util.UUID;

import com.fiap.challenge.parts.dto.PartResponseDTO;

public interface FindPartByIdUseCase {
    public PartResponseDTO execute(UUID id);
}